package com.example.whatch_moovium;


import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.whatch_moovium.API_Interface.DiscoverRequest;
import com.example.whatch_moovium.Model.Movie;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RunWith(AndroidJUnit4.class)
public class DiscoverRequestTest {

    private static final String API_KEY = "f862a1abef6de0d1ca20c51abb9f51ab";

    // Context of the app under test.
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    RequestQueue mQueue = Volley.newRequestQueue(context);

    @Test
    public void testApiToolsMovieParser() throws InterruptedException {

        // set up the input arguments for the DiscoverRequest constructor
        String sort = "popularity.desc";
        boolean flatrate = true;
        List<Integer> providers = new ArrayList<>();
        providers.add(337);
        providers.add(8);
        providers.add(9);
        List<Movie> movieList = new ArrayList<>();

        //Make latch
        CountDownLatch countDownLatch = new CountDownLatch(1);

        //make request
        new DiscoverRequest(mQueue, API_KEY, sort, flatrate, providers, movieList, "", countDownLatch);


        // wait for the request to complete
        countDownLatch.await();
        // verify that the movie list contains the expected number of movies
        Assert.assertEquals(20, movieList.size());

    }

}
