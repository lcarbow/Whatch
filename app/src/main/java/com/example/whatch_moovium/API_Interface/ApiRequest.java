package com.example.whatch_moovium.API_Interface;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.whatch_moovium.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ApiRequest {

    String url;
    CountDownLatch myCountDownLatch;
    RequestQueue mQueue;
    JSONObject jsonObject;
    Boolean succes;

    public ApiRequest(String url, RequestQueue mQueue) {
        this.url = url;
        this.mQueue = mQueue;

        myCountDownLatch = new CountDownLatch(1);
        makeRequest();
    }

    JSONObject getJSON() throws InterruptedException {
        myCountDownLatch.await();
        if (succes) {
            return jsonObject;
        } else {
            throw new InterruptedException();
        }
    }

    private void makeRequest() {

        //make discover request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonMovielist) {

                        //only set JSON
                        jsonObject = jsonMovielist;

                        //set succes
                        succes = true;

                        //release latch
                        myCountDownLatch.countDown();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                succes = false;
            }
        });
        mQueue.add(request);

    }

}