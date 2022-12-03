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
import com.example.whatch_moovium.Aufraeumen.Genre;
import com.example.whatch_moovium.Model.Movie;

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

    }

    //calling class has to implement api
    public void getDiscover(String sort, boolean flatrate, List<Integer> providers, Interfaces.apiDiscoverCallback receiver) {

        //movielist to fill later
        List<Movie> movieList = new ArrayList<>();

        //make thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                //countdownlatch for discover request
                CountDownLatch countDownLatch = new CountDownLatch(1);
//hallöchen

                //make request
                discoverRequest(sort, flatrate, providers, movieList, "", countDownLatch);

                //wait for latch to release
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //set countdownlatch for 20 watchprovider requests
                countDownLatch = new CountDownLatch(20);

                //make 20 watchprovider requests
                for (Movie movie : movieList) {
                    watchProviderRequest(movie, countDownLatch);
                }

                //wait for latch to release - all providers set
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

    public void getDiscover(String sort, boolean flatrate, List<Integer> provider, String genre) {

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

        //make discover request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
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
                                movieParserDiscover(movie, jsonMovie);
                                //add movie to list
                                movieList.add(movie);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //notify thread list is done
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

    public void getAll(String sort, boolean flatrate, List<Integer> providers, Interfaces.apiAllCallback receiver) {

        //make thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                Log.i("AlexDebugging", "get all started");
                /*----get list with all genres----*/
                //make list
                List<Genre> allGenres = new ArrayList<Genre>();
                //make countdownlatch
                CountDownLatch countDownLatch = new CountDownLatch(1);
                //make genres request
                genreRequest(allGenres, countDownLatch);
                //wait for latch
                Log.i("AlexDebugging", "waiting for genrelist");
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("AlexDebugging", "continued");

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
                Log.i("AlexDebugging", "started discover requests, waiting for latch");

                //wait for latch
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("AlexDebugging", "continued");

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
                Log.i("AlexDebugging", "genre thread run");

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

                Log.i("AlexDebugging", "genre thread resumed, list made");

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
        Log.i("AlexDebugging", "genrerequest start");
        //make api request
        String url = "https://api.themoviedb.org/3/genre/movie/list?api_key=f862a1abef6de0d1ca20c51abb9f51ab&language=de-DE";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonGenres) {
                        Log.i("AlexDebugging", "request received");
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
                        Log.i("AlexDebugging", "latch counted down");

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
        Log.i("AlexDebugging", "request sent");
    }

    //takes list with ints and return list with movie objects
    public void getWatchlist(List<Integer> idList, Interfaces.apiWatchlistCallback receiver) {

        //make thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                Log.i("AlexDebugging", "getwatchlist started");

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

                Log.i("AlexDebugging", "getwatchlist movies finished");

                /*----Get watchprovider----*/
                //make countdowmlatch for provider requests
                countDownLatch = new CountDownLatch(movieList.size());

                Log.i("AlexDebugging", "getwatchlist make provider requests");
                for (Movie movie : movieList) {
                    watchProviderRequest(movie, countDownLatch);
                }

                Log.i("AlexDebugging", "getwatchlist wait for provider");

                //wait for latch
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



                Log.i("AlexDebugging", "getwatchlist provider finished");

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

    //parses Movie object from movie json from a discover request
    private void movieParserDiscover(Movie movie, JSONObject jsonMovie) throws JSONException {
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

    //parses Movie object from movie json from an movie request
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
                        getPosterCallback(response, receiver);
                    }
                }, 10000, 10000, null, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(imageRequest);
    }

    private void getPosterCallback(Bitmap img, Interfaces.apiPosterCallback receiver) {
        receiver.receivePoster(img);
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