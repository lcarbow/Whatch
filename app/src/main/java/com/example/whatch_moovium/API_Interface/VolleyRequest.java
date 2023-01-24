package com.example.whatch_moovium.API_Interface;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;

public class VolleyRequest {

    private String url;
    private CountDownLatch myCountDownLatch;
    private RequestQueue mQueue;
    private JSONObject jsonObject;
    private Boolean success;

    public VolleyRequest(String url, RequestQueue mQueue) {
        this.url = url;
        this.mQueue = mQueue;

        myCountDownLatch = new CountDownLatch(1);
        makeRequest();
    }

    public JSONObject getJSON() throws InterruptedException {
        myCountDownLatch.await();
        if (success) {
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
                        success = true;

                        //release latch
                        myCountDownLatch.countDown();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                success = false;
            }
        });
        mQueue.add(request);

    }

}