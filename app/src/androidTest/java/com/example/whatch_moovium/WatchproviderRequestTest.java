package com.example.whatch_moovium;

import static org.junit.Assert.assertNotEquals;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.whatch_moovium.API_Interface.WatchproviderRequest;
import com.example.whatch_moovium.Model.Movie;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

@RunWith(AndroidJUnit4.class)
public class WatchproviderRequestTest {

    // Context of the app under test.
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    RequestQueue mQueue = Volley.newRequestQueue(context);

    private Movie movie;

    @Before
    public void setup() {
        movie = new Movie();
        movie.setId(11);
    }

    @Test
    public void testWatchproviderRequest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        WatchproviderRequest request = new WatchproviderRequest(movie, countDownLatch, mQueue);
        countDownLatch.await();
        Assert.assertTrue(movie.getStreaming().length() > 0);
    }
}