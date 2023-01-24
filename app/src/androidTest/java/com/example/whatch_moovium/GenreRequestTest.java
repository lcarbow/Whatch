package com.example.whatch_moovium;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.whatch_moovium.API_Interface.GenreRequest;
import com.example.whatch_moovium.Genre;

@RunWith(AndroidJUnit4.class)
public class GenreRequestTest {

    private static final String API_KEY = "f862a1abef6de0d1ca20c51abb9f51ab";

    // Context of the app under test.
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    RequestQueue mQueue = Volley.newRequestQueue(context);

    private List<Genre> genres;
    private CountDownLatch countDownLatch;

    @Before
    public void setUp() {
        genres = new ArrayList<>();
        countDownLatch = new CountDownLatch(1);
    }

    @After
    public void tearDown() {
        mQueue = null;
        genres = null;
        countDownLatch = null;
    }

    @Test
    public void testGenreRequest() throws InterruptedException {
        new GenreRequest(mQueue, API_KEY, countDownLatch, genres);
        countDownLatch.await();
        assertNotNull(genres);
        assertEquals(19, genres.size());
        assertEquals("Action", genres.get(0).getName());

        int realID = 28;
        int gotID = genres.get(0).getId();
        assertEquals(realID, gotID);
    }
}
