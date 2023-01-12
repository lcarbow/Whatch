package com.example.whatch_moovium.API_Interface;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.example.whatch_moovium.Model.Movie;
import com.android.volley.RequestQueue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;


@RunWith(MockitoJUnitRunner.class)
public class DiscoverRequestTest {

    @Mock
    RequestQueue mockQueue;

    @Mock
    Context context = mock(Context.class);


    RequestQueue mQueue;

    @Before
    public void setUp() {
        // Initialize mock objects
        MockitoAnnotations.initMocks(this);

        mQueue = Volley.newRequestQueue(context);
    }

    @Test
    public void testDiscoverRequest() throws Exception {
        mQueue = Volley.newRequestQueue(context);

        // Set up test data
        String apiKey = "f862a1abef6de0d1ca20c51abb9f51ab";
        String sort = "popularity.desc";
        boolean flatrate = true;
        List<Integer> providers = new ArrayList<>();
        providers.add(8);
        providers.add(337);
        List<Movie> movieList = new ArrayList<>();
        String genres = "";
        CountDownLatch countDownLatch = new CountDownLatch(1);

        // Initialize DiscoverRequest object with mock objects
        DiscoverRequest discoverRequest = new DiscoverRequest(mockQueue, apiKey, sort, flatrate, providers, movieList, genres, countDownLatch);

        countDownLatch.await();

        // Verify that the movie list is not empty after the discoverRequest method is called
        assertEquals(20, movieList.size());
    }
}
