package com.example.whatch_moovium;

import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.whatch_moovium.API_Interface.MovieRequest;
import com.example.whatch_moovium.Model.Movie;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

@RunWith(AndroidJUnit4.class)
public class MovieRequestTest {

    private static final String API_KEY = "f862a1abef6de0d1ca20c51abb9f51ab";

    // Context of the app
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    RequestQueue mQueue = Volley.newRequestQueue(context);

    @Test
    public void testMovieRequest() throws InterruptedException {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        // Set up a CountDownLatch to wait for the request to finish
        CountDownLatch countDownLatch = new CountDownLatch(1);

        // Create a Movie object to store the response data
        Movie movie = new Movie();

        // Execute the MovieRequest with a valid movie ID
        new MovieRequest(mQueue, API_KEY, countDownLatch, movie, 11);

        // Wait for the request to finish
        countDownLatch.await();

        // Check that the movie object has been updated with data
        assertTrue(movie.getTitle().length() > 0);
        assertTrue(movie.getDescription().length() > 0);
        assertTrue(movie.getReleaseDate().length() > 0);
        assertTrue(movie.getBackdrop().length() > 0);
        assertTrue(movie.getPoster().length() > 0);
        assertTrue(movie.getStreaming().length() > 0);
        assertTrue(movie.getId() > 0);
        assertTrue(movie.getGenre().length() > 0);
        assertTrue(movie.getOriginal_language().length() > 0);

    }
}
