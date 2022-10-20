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
import java.util.Collections;
import java.util.List;


public class API_Interface {

    private RequestQueue mQueue;
    TextView testOutputFiltered;
    private int startedCalls = 0;
    //api key
    private String apiKey = "f862a1abef6de0d1ca20c51abb9f51ab";

    apiInterfaceCallback myCallback;

    public void setCallback(apiInterfaceCallback myCallback) {
        this.myCallback = myCallback;
    }

    public API_Interface(Context context) {
        mQueue = Volley.newRequestQueue(context);
    }

    //einen zufälligen film zurückgeben, ausgesucht aus den trending filmen des tages
    public void getRandom() {

        this.testOutputFiltered = testOutputFiltered;

        List<Movie> movieList = new ArrayList<>();

        getTrending(movieList);

    }

    void getTrending(List<Movie> movieList) {

        String url = "https://api.themoviedb.org/3/trending/movie/week?api_key=" + apiKey;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject trendingList) {

                        try {
                            //get array of movies from response
                            JSONArray results = trendingList.getJSONArray("results");

                            //make movie array
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject jsonMovie = results.getJSONObject(i);

                                //make new movie object
                                Movie movie = new Movie(jsonMovie.getString("title"), jsonMovie.getInt("id"), jsonMovie.getString("overview"), jsonMovie.getDouble("vote_average"), "null", jsonMovie.getString("backdrop_path"), "");

                                //add movie to list
                                movieList.add(movie);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                        filterByProvider(movieList);

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
    private void filterByProvider(List<Movie> movieList) {

        List<Movie> filteredMovieList = new ArrayList<>();

        startedCalls = movieList.size();
        for (Movie movie : movieList) {
            getWatchProvider(movie.getId(), movie.getTitle(), movie, filteredMovieList);
        }

    }

    public void getWatchProvider(int movieID, String title, Movie movie, List<Movie> filteredMovieList) {

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

        //Movieliste ist fertig
        if (startedCalls == 0) {
            //Log.i("UserLogging", "all calls done, found movies: " + filteredMovieList.size());
            for (Movie tempMovie : filteredMovieList) {
                //testOutputFiltered.append(tempMovie.getTitle() + "\n");
            }

            Collections.shuffle(filteredMovieList);
            myCallback.displayMovie(filteredMovieList);
        }
    }

    public interface apiInterfaceCallback {
        void displayMovie(List<Movie> filteredMovieList);
    }

}