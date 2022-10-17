package com.example.whatch_moovium;

import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class API_Interface {

    private RequestQueue mQueue;

    //api key
    private String apiKey = "f862a1abef6de0d1ca20c51abb9f51ab";

    //nimmt eine Liste filme entgegen und filtered die raus die nicht in den eigenen providern sind
    private void filterByProvider() {

    }

    //einen zufälligen film zurückgeben, ausgesucht aus den trending filmen des tages
    public void getRandom() {

        String url = "https://api.themoviedb.org/3/trending/movie/day?api_key=" + apiKey;
        List<Movie> movieList = new ArrayList<>();

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
                                //Movie movie = new Movie(jsonMovie.getString("title"));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
