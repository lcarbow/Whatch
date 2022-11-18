package com.example.whatch_moovium.API_Interface;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.whatch_moovium.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DiscoverRequest {

    private RequestQueue mQueue;
    private String apiKey = "f862a1abef6de0d1ca20c51abb9f51ab";

    //make thread
    public void getDiscoverThread(String sort, boolean flatrate, List<Integer> providers, Interfaces.apiDiscoverCallback receiver, RequestQueue mQueue) {

        this.mQueue = mQueue;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("AlexDebugging", "thread run");

                //countdownlatch for discover request
                CountDownLatch countDownLatch = new CountDownLatch(1);

                //movielist to fill later
                List<Movie> movieList = new ArrayList<>();

                //make request
                discoverRequest(sort, flatrate, providers, movieList, countDownLatch);

                //wait for latch to release
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.i("AlexDebugging", "thread resumed, list made");

                //set countdownlatch for 20 watchprovider requests
                countDownLatch = new CountDownLatch(20);

                //make 20 watchprovider requests
                for (Movie movie : movieList) {
                    watchProviderRequest(movie, countDownLatch);
                }

                //wait for latch to release
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                //return movielist
                //receiver.receiveDiscover(movieList);
                for (Movie movie : movieList) {
                    Log.i("AlexDebugging", "Title: " + movie.getTitle() + " Providers: " + movie.getStreaming());
                }

            }
        });
        thread.start();

    }

    private void discoverRequest(String sort, boolean flatrate, List<Integer> providers, List<Movie> movieList, CountDownLatch countDownLatch) {

        //make discover request
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + apiKey + "&language=de-DE&region=DE&sort_by=" + sort + "&include_adult=false&include_video=false&page=1";

        //if only show from own streaming services
        if (flatrate) {
            //make provider string
            String providersString = "";
            for (Integer providerINT : providers) {
                providersString += Integer.toString(providerINT);
                providersString += "%7C";
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
                                Movie movie = movieParser(jsonMovie);
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

    //parses Movie object from movie json
    private Movie movieParser(JSONObject jsonMovie) throws JSONException {
        //make new movie object
        Movie movie = new Movie();
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

        return movie;
    }

    //takes movie and makes api request for it
    void watchProviderRequest(Movie movie, CountDownLatch countDownLatch) {

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

}
