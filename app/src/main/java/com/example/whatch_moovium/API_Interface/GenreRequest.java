package com.example.whatch_moovium.API_Interface;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.whatch_moovium.Genre;
import com.example.whatch_moovium.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class GenreRequest {

    private RequestQueue mQueue;
    private String apiKey;
    private CountDownLatch countDownLatch;
    private List<Genre> genres;

    public GenreRequest(RequestQueue mQueue, String apiKey, CountDownLatch countDownLatch, List<Genre> genres) {
        this.mQueue = mQueue;
        this.apiKey = apiKey;
        this.countDownLatch = countDownLatch;
        this.genres = genres;

        genreRequest();
    }

    void genreRequest() {

        //make thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                //make api request
                String url = "https://api.themoviedb.org/3/genre/movie/list?api_key=f862a1abef6de0d1ca20c51abb9f51ab&language=de-DE";

                //apirequest
                ApiRequest genreApiRequest = new ApiRequest(url, mQueue);
                JSONObject jsonGenres = null;
                try {jsonGenres = genreApiRequest.getJSON();} catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.i("Alex", "ApiRequest Error!!!");
                }

                //process json
                try {
                    JSONArray jsonGenreList = jsonGenres.getJSONArray("genres");

                    for (int i = 0; i < jsonGenreList.length(); i++) {
                        JSONObject jsonGenre = jsonGenreList.getJSONObject(i);
                        genres.add(new Genre(jsonGenre.getString("name"), jsonGenre.getInt("id")));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //release latch because genreList is done
                countDownLatch.countDown();

            }
        });
        thread.start();

    }
}