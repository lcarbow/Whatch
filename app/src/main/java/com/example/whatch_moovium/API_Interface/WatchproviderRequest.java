package com.example.whatch_moovium.API_Interface;

import android.util.Log;

import androidx.constraintlayout.utils.widget.MockView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.whatch_moovium.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;

public class WatchproviderRequest {

    private Movie movie;
    private CountDownLatch countDownLatch;
    private RequestQueue mQueue;

    public WatchproviderRequest(Movie movie, CountDownLatch countDownLatch, RequestQueue mQueue) {
        this.movie = movie;
        this.countDownLatch = countDownLatch;
        this.mQueue = mQueue;

        watchProviderRequest();
    }




    //takes movie and makes api request for watchprovider
    private void watchProviderRequest() {

        //make api request
        String url = "https://api.themoviedb.org/3/movie/" + movie.getId() + "/watch/providers?api_key=" + "f862a1abef6de0d1ca20c51abb9f51ab";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject JSONwatchProviders) {

                        //Log.i("Alex", "json watchprovider receiced");

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
                //Log.i("Alex", "json watchprovider error");
            }
        });
        mQueue.add(request);
        //Log.i("Alex", " watchprovider request added to queue");
    }

}
