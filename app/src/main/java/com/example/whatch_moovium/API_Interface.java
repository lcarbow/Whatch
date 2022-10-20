package com.example.whatch_moovium;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class API_Interface {

    public API_Interface(Context context) {
        mQueue = Volley.newRequestQueue(context);
    }

    private RequestQueue mQueue;

    private int startedCalls = 0;


    //api key
    private String apiKey = "f862a1abef6de0d1ca20c51abb9f51ab";



    //einen zufälligen film zurückgeben, ausgesucht aus den trending filmen des tages
    public void getRandom(TextView testOutput, TextView testOutputProviders) {

        List<Movie> movieList = new ArrayList<>();

        getTrending(movieList, testOutput, testOutputProviders);

    }

    void getTrending(List<Movie> movieList, TextView testOutput, TextView testOutputProviders) {

        String url = "https://api.themoviedb.org/3/trending/movie/week?api_key=" + apiKey;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject trendingList) {

                        try {
                            //get array of movies from response
                            JSONArray results = trendingList.getJSONArray("results");

                            testOutput.setText("");
                            //make movie array
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject jsonMovie = results.getJSONObject(i);

                                //make new movie object
                                Movie movie = new Movie(jsonMovie.getString("title"), jsonMovie.getInt("id"), jsonMovie.getString("overview"), jsonMovie.getDouble("vote_average"), "null", jsonMovie.getString("backdrop_path"), "");

                                //add movie to list
                                movieList.add(movie);

                                //add movie to textview
                                testOutput.append(jsonMovie.getString("title") + "\n");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        testOutput.setText("");
                        for (Movie movie : movieList) {
                            testOutput.append(movie.getTitle() + " " + movie.getId() + "\n");
                        }

                        filterByProvider(movieList, testOutputProviders);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }

    //nimmt eine Liste filme entgegen und filtered die raus die nicht in den eigenen providern sind
    private void filterByProvider(List<Movie> movieList, TextView testOutputProviders) {

        List<Movie> filteredMovieList = new ArrayList<>();

        startedCalls = movieList.size();
        testOutputProviders.setText("");
        for (Movie movie : movieList) {
            getWatchProvider(movie.getId(), movie.getTitle(), movie, filteredMovieList, testOutputProviders);
        }

    }

    public void getWatchProvider(int movieID, String title, Movie movie, List<Movie> filteredMovieList, TextView testOutputProviders) {

        String url = "https://api.themoviedb.org/3/movie/" + movieID + "/watch/providers?api_key=" + apiKey;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject JSONwatchProviders) {

                        String providers = "";
                        List<String> watchProviders = new ArrayList<>();

                        try {

                            JSONObject results = JSONwatchProviders.getJSONObject("results");
                            JSONObject DE = results.getJSONObject("DE");
                            JSONArray flatrate = DE.getJSONArray("flatrate");

                            for (int i = 0; i < flatrate.length(); i++) {
                                JSONObject provider = flatrate.getJSONObject(i);
                                providers += provider.getString("provider_name") + " ";
                                watchProviders.add(provider.getString("provider_name"));
                            }

                            testOutputProviders.append(title + ": " + providers + "\n");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //call callback
                        watchProviderCallback(movie, watchProviders, filteredMovieList);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);

    }

    void watchProviderCallback(Movie movie, List<String> watchProviders, List<Movie> filteredMovieList) {

        startedCalls--;

        for (String watchProvider : watchProviders) {
            //Log.i("UserLogging", movie.getTitle() + " " + watchProvider);
            if (watchProvider.equalsIgnoreCase("Netflix")) {
                filteredMovieList.add(movie);
            }
        }

        if (startedCalls == 0) {
            Log.i("UserLogging", "all calls done, found movies: " + filteredMovieList.size());
        }
    }

}