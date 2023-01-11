package com.example.whatch_moovium.API_Interface;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.example.whatch_moovium.Model.Movie;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;

public class MovieRequest {

    private RequestQueue mQueue;
    private String apiKey;
    CountDownLatch countDownLatch;
    Movie movie;
    int id;

    public MovieRequest(RequestQueue mQueue, String apiKey, CountDownLatch countDownLatch, Movie movie, int id) {
        this.mQueue = mQueue;
        this.apiKey = apiKey;
        this.countDownLatch = countDownLatch;
        this.movie = movie;
        this.id = id;

        movieRequest();
    }

    void movieRequest() {

        //make thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                //make api request
                String url = " https://api.themoviedb.org/3/movie/" + id + "?api_key=f862a1abef6de0d1ca20c51abb9f51ab&language=de-DE";

                //api request
                //apirequest
                VolleyRequest movieVolleyRequest = new VolleyRequest(url, mQueue);
                JSONObject jsonMovie = null;
                try {jsonMovie = movieVolleyRequest.getJSON();} catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.i("Alex", "ApiRequest Error!!!");
                }

                //process json
                try {
                    ApiTools.movieParserMovie(movie, jsonMovie);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //add provider
                CountDownLatch myLatch = new CountDownLatch(1);
                new WatchproviderRequest(movie, myLatch, mQueue);
                try {
                    myLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                countDownLatch.countDown();

            }
        });
        thread.start();

    }

}
