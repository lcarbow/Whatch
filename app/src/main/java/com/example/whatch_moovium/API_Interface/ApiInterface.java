package com.example.whatch_moovium.API_Interface;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.whatch_moovium.Genre;
import com.example.whatch_moovium.Model.Movie;
import com.example.whatch_moovium.Presenter.MoodSuggPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class ApiInterface {

    private RequestQueue mQueue;
    private Activity activity;
    //api key
    private static String apiKey = "f862a1abef6de0d1ca20c51abb9f51ab";

    //constructor
    public ApiInterface(Context context) {
        mQueue = Volley.newRequestQueue(context);
        this.activity = (Activity) context;

        //getall test
        /*List<Integer> providerList = new ArrayList<>();
        providerList.add(8);
        providerList.add(337);
        getAll("popularity.desc", true, providerList);*/

        //watchlist test
        /*List<Integer> idList = new ArrayList<Integer>();
        idList.add(438631);
        idList.add(361743);
        idList.add(634649);
        getWatchlist(idList);*/

        /*//getwatchprovidertest
        Movie movie = new Movie();
        movie.setId(438631);
        getWatchprovider(movie, this);*/

        //get similar test
        /*List<String> providerList = new ArrayList<String>();
        providerList.add("Disney Plus");
        getSimilar(11, true, providerList, 20);*/

    }

    //calling class has to implement api interface
    public void getDiscover(String sort, boolean flatrate, List<Integer> providers, Interfaces.apiDiscoverCallback receiver) {

        //movielist to fill later
        List<Movie> movieList = new ArrayList<>();

        //make thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                //countdownlatch for discover request
                CountDownLatch countDownLatch = new CountDownLatch(1);

                //make request
                discoverRequest(sort, flatrate, providers, movieList, "", countDownLatch);

                //wait for latch to release
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //return request
                activity.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        receiver.receiveDiscover(movieList);
                    }
                });

            }
        });
        thread.start();


    }

    //makes the api request for discover
    private void discoverRequest(String sort, boolean flatrate, List<Integer> providers, List<Movie> movieList, String genres, CountDownLatch countDownLatch) {

        //make discover request
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + apiKey + "&language=de-DE&region=DE&sort_by=" + sort + "&include_adult=false&include_video=false&page=1&with_genres=" + genres;

        //if only show from own streaming services
        if (flatrate) {
            //make provider string
            String providersString = "";
            for (Integer providerINT : providers) {
                providersString += Integer.toString(providerINT);
                providersString += "%7C";//
            }
            url += "&with_watch_providers=" + providersString + "&watch_region=DE&with_watch_monetization_types=flatrate";
        }

        //make thread
        String finalUrl = url;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                //create countdownlatch for this thread - set to 1 for discover request
                CountDownLatch countDownLatch1 = new CountDownLatch(1);

                //make discover request
                CountDownLatch finalCountDownLatch = countDownLatch1;
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, finalUrl, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonMovielist) {

                                //process json
                                try {
                                    //get array of movies from response
                                    JSONArray results = jsonMovielist.getJSONArray("results");

                                    //make movie array
                                    for (int i = 0; i < results.length(); i++) {
                                        //get json for single movie
                                        JSONObject jsonMovie = results.getJSONObject(i);
                                        //parse movie form json
                                        Movie movie = new Movie();
                                        movieParser(movie, jsonMovie);
                                        //add movie to list
                                        movieList.add(movie);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                //notify thread list is done
                                finalCountDownLatch.countDown();


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                mQueue.add(request);

                //wait till request is done
                try {
                    countDownLatch1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //set latch for provider requests
                countDownLatch1 = new CountDownLatch(movieList.size());

                //make provider requests
                for (Movie movie : movieList) {
                    watchProviderRequest(movie, countDownLatch1);
                }

                //wait for provider requests
                try {
                    countDownLatch1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //release given latch
                countDownLatch.countDown();

            }
        });
        thread.start();


    }

    public void getAll(String sort, boolean flatrate, List<Integer> providers, Interfaces.apiAllCallback receiver) {

        //make thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {


                /*----get list with all genres----*/
                //make list
                List<Genre> allGenres = new ArrayList<Genre>();
                //make countdownlatch
                CountDownLatch countDownLatch = new CountDownLatch(1);
                //make genres request
                genreRequest(allGenres, countDownLatch);
                //wait for latch
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                /*----Make lists with lists----*/
                List<List> allList = new ArrayList<List>();

                /*----make discover requests for each list----*/
                //new latch
                countDownLatch = new CountDownLatch(allGenres.size());
                //make list and request for each genre
                for (Genre genre : allGenres) {
                    List<Movie> genreMovieList = new ArrayList<Movie>();
                    discoverRequest(sort, flatrate, providers, genreMovieList, genre.getId().toString(), countDownLatch);
                    allList.add(genreMovieList);
                }


                //wait for latch
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
/*
                *//*----add providers to all movies----*//*

                //new latch
                countDownLatch = new CountDownLatch(380);
                //make requests
                for (List<Movie> list : allList) {
                    for (Movie movie : list) {
                        watchProviderRequest(movie, countDownLatch);
                    }
                }*/



                //return lists


                activity.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        receiver.receiveAll(allList);
                    }
                });
                //Print Lists
                /*for (List list : allList) {
                    for (Object object : list) {
                        Movie movie = (Movie) object;
                        Log.i("AlexDebugging", movie.toString());
                    }
                }*/

            }
        });
        thread.start();

    }

    //gets list of all genres
    public void getGenres(Interfaces.apiGenreCallback receiver) {

        List<Genre> genres = new ArrayList<Genre>();

        //make thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                //countdownlatch for discover request
                CountDownLatch countDownLatch = new CountDownLatch(1);

                //make request
                genreRequest(genres, countDownLatch);

                //wait for latch to release
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //return request
                activity.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        receiver.receiveGenres(genres);
                    }
                });

            }
        });
        thread.start();

    }

    //makes genres request and fills genres list
    private void genreRequest(List<Genre> genres, CountDownLatch countDownLatch) {

        //make api request
        String url = "https://api.themoviedb.org/3/genre/movie/list?api_key=f862a1abef6de0d1ca20c51abb9f51ab&language=de-DE";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonGenres) {

                        try {

                            JSONArray jsonGenreList = jsonGenres.getJSONArray("genres");

                            for (int i = 0; i < jsonGenreList.length(); i++) {
                                JSONObject jsonGenre = jsonGenreList.getJSONObject(i);
                                genres.add(new Genre(jsonGenre.getString("name"), jsonGenre.getInt("id")));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //countdown latch
                        countDownLatch.countDown();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);

    }

    //takes list with ints and return list with movie objects
    public void getWatchlist(List<Integer> idList, Interfaces.apiWatchlistCallback receiver) {

        //make thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                //make Movielist
                List<Movie> movieList = new ArrayList<Movie>();

                //make countdowmlatch for movie requests
                CountDownLatch countDownLatch = new CountDownLatch(idList.size());

                for (Integer id : idList) {
                    Movie movie = new Movie();
                    movieRequest(movie, id, countDownLatch);
                    movieList.add(movie);
                }

                //wait for latch
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /*----Get watchprovider----*/
                //make countdowmlatch for provider requests
                countDownLatch = new CountDownLatch(movieList.size());

                for (Movie movie : movieList) {
                    watchProviderRequest(movie, countDownLatch);
                }

                //wait for latch
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //print list
                /*for (Movie movie : movieList) {
                    Log.i("AlexDebugging", movie.toString());
                }*/

                //return movielist
                activity.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        receiver.receiveWatchlist(movieList);
                    }
                });

            }
        });
        thread.start();

    }

    private void movieRequest(Movie movie, int id, CountDownLatch countDownLatch) {

        //make api request
        String url = " https://api.themoviedb.org/3/movie/" + id + "?api_key=f862a1abef6de0d1ca20c51abb9f51ab&language=de-DE";
//https://api.themoviedb.org/3/movie/
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonMovie) {

                        /*try {

                            movie.setTitle(jsonMovie.getString("title"));
                            movie.setId(jsonMovie.getInt("id"));
                            movie.setImdbID(jsonMovie.getInt("imdb_id"));
                            movie.setDescription(jsonMovie.getString("title"));
                            movie.setRating(jsonMovie.getString("title"));
                            //set genre
                            movie.setPoster(jsonMovie.getString("title"));
                            movie.setBackdrop(jsonMovie.getString("title"));
                            movie.setReleaseDate(jsonMovie.getString("title"));
                            //set streaming
                            movie.setOriginal_language(jsonMovie.getString("title"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/

                        try {
                            movieParserMovie(movie, jsonMovie);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //countdown latch
                        countDownLatch.countDown();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    public void getWatchprovider(Movie movie, Interfaces.apiWatchproviderCallback receiver) {

        //make thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                //make countdownlatch
                CountDownLatch countDownLatch = new CountDownLatch(1);

                //providerrequest
                watchProviderRequest(movie, countDownLatch);

                //wait for latch
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //return movie now with provider
                activity.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        receiver.receiveWatchprovider(movie);
                    }
                });

            }
        });
        thread.start();

    }

    //takes movie and makes api request for watchprovider
    private void watchProviderRequest(Movie movie, CountDownLatch countDownLatch) {

        //make api request
        String url = "https://api.themoviedb.org/3/movie/" + movie.getId() + "/watch/providers?api_key=" + apiKey;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject JSONwatchProviders) {

                        String providers = "";

                        try {
                            JSONObject results = JSONwatchProviders.getJSONObject("results");
                            JSONObject DE = results.getJSONObject("DE");
                            JSONArray flatrate = DE.getJSONArray("flatrate");

                            for (int i = 0; i < flatrate.length(); i++) {
                                JSONObject provider = flatrate.getJSONObject(i);
                                providers += provider.getString("provider_name") + " ";
                                movie.addStreaming(provider.getString("provider_name"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Log.i("Alex", "exception ");
                            countDownLatch.countDown();
                        }

                        //countdown latch
                        countDownLatch.countDown();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    //takes a movie and returns similar movies, the returned list is at least "minListLength" long
    public void getSimilar(int movieID, boolean flatrate, List<String> providers, int minListLength, Interfaces.apiSimilarCallback receiver) {

        // CHANGE: take provider int List instead of string list

        //make thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                //sililarlist
                List<Movie> similarList = new ArrayList<Movie>();

                int page = 0;

                // TODO: 15.12.22 add a maximum of loop runs in case there are not enough similar movies
                //loop until list is long enough
                while (similarList.size() < minListLength) {

                    //increase page by one for the next page
                    page++;

                    //Log.i("Alex", "loop");

                    //make temp Movie List
                    List<Movie> tempList = new ArrayList<Movie>();

                    //make countdownlatch for similarrequest
                    CountDownLatch countDownLatch = new CountDownLatch(1);

                    //make request
                    SimilarRequest similarRequest = new SimilarRequest(mQueue, apiKey, countDownLatch, movieID, tempList, page);

                    //wait for latch
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //Log.i("Alex", "similar request done");

                    //add providers
                    //make countdownlatch for 20 movies
                    countDownLatch = new CountDownLatch(tempList.size());

                    //make 20 provider requests
                    for (Movie movie : tempList) {
                        watchProviderRequest(movie, countDownLatch);
                    }

                    //wait for latch
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //Log.i("Alex", "watchprovider Request done");

                    //filter by providers - add to similarList
                    for (Movie movie : tempList) {
                        if (flatrate) {
                            for (String service : providers) {
                                if (movie.isAvailableAt(service)) {
                                    similarList.add(movie);
                                    break;
                                }
                            }
                        } else {
                            similarList.add(movie);
                        }
                    }


                }

                //printing result list
                /*Log.i("Alex", "Printing all movies");
                for (Movie movie : similarList) {
                    Log.i("AlexDebugingResult", movie.toString());
                }*/

                //return movielist
                activity.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        receiver.receiveSimilar(similarList);
                    }
                });

            }
        });
        thread.start();

    }

    //parses Movie object from movie json from a discover request and similarRequest
    public static void movieParser(Movie movie, JSONObject jsonMovie) throws JSONException {
        //make new movie object
        movie.setTitle(jsonMovie.getString("title"));
        movie.setId(jsonMovie.getInt("id"));
        movie.setDescription(jsonMovie.getString("overview"));
        movie.setRating(jsonMovie.getDouble("vote_average"));
        //add genre ids
        JSONArray jsonGenres = jsonMovie.getJSONArray("genre_ids");
        for (int j = 0; j < jsonGenres.length(); j++) {
            movie.addGenre(jsonGenres.getInt(j));
        }
        movie.setPoster(jsonMovie.getString("poster_path"));
        movie.setBackdrop(jsonMovie.getString("backdrop_path"));
        movie.setReleaseDate(jsonMovie.getString("release_date"));
        movie.setOriginal_language(jsonMovie.getString("original_language"));

    }

    //parses Movie object from movie json from a movie request
    private void movieParserMovie(Movie movie, JSONObject jsonMovie) throws JSONException {
        //make new movie object
        movie.setTitle(jsonMovie.getString("title"));
        movie.setId(jsonMovie.getInt("id"));
        movie.setDescription(jsonMovie.getString("overview"));
        movie.setRating(jsonMovie.getDouble("vote_average"));
        //add genre ids
        JSONArray jsonGenres = jsonMovie.getJSONArray("genres");
        for (int j = 0; j < jsonGenres.length(); j++) {
            movie.addGenre(jsonGenres.getJSONObject(j).getInt("id"));
        }
        movie.setPoster(jsonMovie.getString("poster_path"));
        movie.setBackdrop(jsonMovie.getString("backdrop_path"));
        movie.setReleaseDate(jsonMovie.getString("release_date"));
        movie.setOriginal_language(jsonMovie.getString("original_language"));

    }

    //poster functions
    public void getPoster(String imgPath, Interfaces.apiPosterCallback receiver) {

        String url = "https://image.tmdb.org/t/p/w780" + imgPath;

        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {

                        activity.runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                receiver.receivePoster(response);
                            }
                        });

                    }
                }, 10000, 10000, null, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(imageRequest);
    }

    //backdrop functions
    public void getBackdrop(String imgPath, Interfaces.apiBackdropCallback receiver) {

        String url = "https://image.tmdb.org/t/p/original" + imgPath;

        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        getBackdropCallback(response, receiver);
                    }
                }, 10000, 10000, null, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(imageRequest);
    }

    private void getBackdropCallback(Bitmap img, Interfaces.apiBackdropCallback receiver) {
        receiver.receiveBackdrop(img);
    }

}