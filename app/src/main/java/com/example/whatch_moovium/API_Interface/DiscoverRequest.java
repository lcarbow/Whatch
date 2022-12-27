package com.example.whatch_moovium.API_Interface;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.whatch_moovium.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DiscoverRequest {

    private RequestQueue mQueue;
    private String apiKey = "f862a1abef6de0d1ca20c51abb9f51ab";
    String sort;
    boolean flatrate;
    List<Integer> providers;
    List<Movie> movieList;
    String genres;
    CountDownLatch countDownLatch;

    public DiscoverRequest(RequestQueue mQueue, String apiKey, String sort, boolean flatrate, List<Integer> providers, List<Movie> movieList, String genres, CountDownLatch countDownLatch) {
        this.mQueue = mQueue;
        this.apiKey = apiKey;
        this.sort = sort;
        this.flatrate = flatrate;
        this.providers = providers;
        this.movieList = movieList;
        this.genres = genres;
        this.countDownLatch = countDownLatch;

        discoverRequest();
    }

    //makes the api request for discover - also add flatrate providers with the providerRequest
    private void discoverRequest() {

        //make thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                //make discover url
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


                //make apiRequest
                ApiRequest discoverApiRequest = new ApiRequest(url, mQueue);
                JSONObject jsonMovielist = null;
                try {jsonMovielist = discoverApiRequest.getJSON();} catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.i("Alex", "ApiRequest Error!!!");
                }

                //process json movie List
                try {
                    //get array of movies from response
                    JSONArray results = jsonMovielist.getJSONArray("results");

                    //make movie array
                    for (int i = 0; i < results.length(); i++) {
                        //get json for single movie
                        JSONObject jsonMovie = results.getJSONObject(i);
                        //parse movie form json
                        Movie movie = new Movie();
                        ApiTools.movieParser(movie, jsonMovie);
                        //add movie to list
                        movieList.add(movie);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //Add provider

                //set latch for provider requests
                CountDownLatch countDownLatchApiRequest = new CountDownLatch(movieList.size());

                Log.i("Alex", "before provider request");

                //make provider requests
                for (Movie movie : movieList) {
                    new WatchproviderRequest(movie, countDownLatchApiRequest, mQueue);
                }
                Log.i("Alex", "after provider request");

                //wait for provider requests
                try {
                    countDownLatchApiRequest.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.i("Alex", "provider request done latch");

                //release given latch
                countDownLatch.countDown();

            }
        });
        thread.start();


    }

}
