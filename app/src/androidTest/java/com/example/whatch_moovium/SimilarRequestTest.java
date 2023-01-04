package com.example.whatch_moovium;


import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.whatch_moovium.API_Interface.SimilarRequest;
import com.example.whatch_moovium.Model.Movie;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RunWith(AndroidJUnit4.class)
public class SimilarRequestTest {

    private static final String API_KEY = "f862a1abef6de0d1ca20c51abb9f51ab";

    // Context of the app under test.
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    RequestQueue mQueue = Volley.newRequestQueue(context);

    @Test
    public void testSimilarRequest() throws InterruptedException {
        // Get the activity instance

        // Set up a CountDownLatch to wait for the similar movies to be loaded
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        // Set up a List to store the similar movies
        final List<Movie> similarMovies = new ArrayList<>();

        // Create a SimilarRequest instance to get the similar movies for a movie with ID 123
        new SimilarRequest(mQueue, API_KEY, countDownLatch, 11, similarMovies, 1);

        // Wait for the similar movies to be loaded
        countDownLatch.await();

        // Verify that the similar movies list is not empty
        Assert.assertTrue(similarMovies.size() > 0);

    }
}
