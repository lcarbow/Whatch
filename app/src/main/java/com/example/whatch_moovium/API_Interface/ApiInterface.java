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
    private Context context;
    //api key
    private static String apiKey = "f862a1abef6de0d1ca20c51abb9f51ab";

    //constructor
    public ApiInterface(Context context) {
        mQueue = Volley.newRequestQueue(context);
        this.activity = (Activity) context;
        this.context = context;

        //getall test
        /*List<Integer> providerList = new ArrayList<>();
        providerList.add(8);
        providerList.add(337);
        getAll("popularity.desc", true, providerList);*/

        //discover test


        //watchlist test
        /*List<Integer> idList = new ArrayList<Integer>();
        idList.add(438631);
        idList.add(361743);
        idList.add(634649);
        getWatchlist(idList, this);*/

        /*//getwatchprovidertest
        Movie movie = new Movie();
        movie.setId(438631);
        getWatchprovider(movie, this);*/

        //get similar test
        /*List<String> providerList = new ArrayList<String>();
        providerList.add("Disney Plus");
        providerList.add("Netflix");
        List<Integer> movieIDs = new ArrayList<Integer>();
        movieIDs.add(438631);
        movieIDs.add(361743);
        movieIDs.add(634649);
        getSimilar(movieIDs, true, providerList, 20, this);*/

        //try watchproviderconverter
        WatchProviderConverter watchProviderConverter = WatchProviderConverter.getInstance(context, apiKey);
        //new thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i("Alex", watchProviderConverter.getWatchProviderId("Netflix").toString());
                    Log.i("Alex", watchProviderConverter.getWatchProviderName(8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();





    }

    //calling class has to implement api interface
    public void getDiscover(String sort, boolean flatrate, List<String> providerStrings, Interfaces.apiDiscoverCallback receiver) {

        //make thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                //convert provider strings to ids
                List<Integer> providerIDs = null;
                try {
                    providerIDs = WatchProviderConverter.getInstance(context, apiKey).stringListToIDs(providerStrings);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //movielist to fill later
                List<Movie> movieList = new ArrayList<>();

                //make latch for discoverRequest
                CountDownLatch countDownLatchDiscover = new CountDownLatch(1);

                //make discover request object
                new DiscoverRequest(mQueue, apiKey, sort, flatrate, providerIDs, movieList, "", countDownLatchDiscover);

                //wait for latch to release
                try {
                    countDownLatchDiscover.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //return list
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        receiver.receiveDiscover(movieList);
                    }
                });

            }
        });
        thread.start();

    }

    public void getAll(String sort, boolean flatrate, List<String> providerStrings, Interfaces.apiAllCallback receiver) {

        //make thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                //convert provider strings to ids
                List<Integer> providerIDs = null;
                try {
                    providerIDs = WatchProviderConverter.getInstance(context, apiKey).stringListToIDs(providerStrings);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /*----get list with all genres----*/
                //make list
                List<Genre> allGenres = new ArrayList<Genre>();
                //make countdownlatch
                CountDownLatch countDownLatch = new CountDownLatch(1);
                //make genres request
                new GenreRequest(mQueue, apiKey, countDownLatch, allGenres);
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
                    //discoverRequest(sort, flatrate, providers, genreMovieList, genre.getId().toString(), countDownLatch);
                    new DiscoverRequest(mQueue, apiKey, sort, flatrate, providerIDs, genreMovieList, genre.getId().toString(), countDownLatch);
                    allList.add(genreMovieList);
                }
                //wait for latch
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //return list
                activity.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        receiver.receiveAll(allList);
                    }
                });

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
                //genreRequest(genres, countDownLatch);
                new GenreRequest(mQueue, apiKey, countDownLatch, genres);

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
                    //movieRequest(movie, id, countDownLatch);
                    new MovieRequest(mQueue, apiKey, countDownLatch, movie, id);
                    movieList.add(movie);
                }

                //wait for latch
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

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

    //takes a list of movieIDs and returns similar movies, the returned list is at least "minListLength" long
    public void getSimilar(List<Integer> movieIDs, boolean flatrate, List<String> providers, int minListLength, Interfaces.apiSimilarCallback receiver) {

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

                    //make temp Movie List
                    List<Movie> tempList = new ArrayList<Movie>();

                    //make countdownlatch for similarrequest
                    CountDownLatch countDownLatch = new CountDownLatch(movieIDs.size());

                    //make requests for all movieIDs
                    for (int movieID : movieIDs) {
                        //make request
                        SimilarRequest similarRequest = new SimilarRequest(mQueue, apiKey, countDownLatch, movieID, tempList, page);
                    }

                    //wait for latch
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //add providers
                    //make countdownlatch for 20 movies
                    countDownLatch = new CountDownLatch(tempList.size());

                    //make 20 provider requests
                    for (Movie movie : tempList) {
                        //watchProviderRequest(movie, countDownLatch);
                        new WatchproviderRequest(movie, countDownLatch, mQueue);
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
                        receiver.receiveBackdrop(response);
                    }
                }, 10000, 10000, null, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(imageRequest);
    }

}