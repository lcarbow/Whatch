package com.example.whatch_moovium.API_Interface;

import android.app.Activity;

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

public class SimilarRequest extends ApiRequest {

    private RequestQueue mQueue;
    private static String apiKey;
    private CountDownLatch countDownLatch;
    private int movieID;
    private List<Movie> similarList;
    private int page;

    public SimilarRequest(RequestQueue mQueue, String apiKey, CountDownLatch countDownLatch, int movieID, List<Movie> similarList, int page) {
        this.mQueue = mQueue;
        this.apiKey = apiKey;
        this.countDownLatch = countDownLatch;
        this.movieID = movieID;
        this.similarList = similarList;
        this.page = page;
        makeRequest();
    }

    public void makeRequest() {

        //make url
        String url = "https://api.themoviedb.org/3/movie/" + movieID + "/similar?api_key=" + apiKey + "&language=de-DE&page=" + page;

        //make request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray results = response.getJSONArray("results");

                            for (int i = 0; i < results.length(); i++) {
                                JSONObject jsonMovie = results.getJSONObject(i);
                                Movie movie = new Movie();
                                ApiInterface.movieParser(movie, jsonMovie);
                                similarList.add(movie);
                            }

                            countDownLatch.countDown();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        mQueue.add(request);

    }
}
