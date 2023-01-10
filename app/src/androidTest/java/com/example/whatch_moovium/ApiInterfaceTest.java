package com.example.whatch_moovium;

import androidx.test.core.app.ActivityScenario;

import com.example.whatch_moovium.Model.Movie;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RunWith(MockitoJUnitRunner.class)
public class ApiInterfaceTest {

    @Test
    public void testGetDiscover() {

        List<Movie> movieList = new ArrayList<>();

        CountDownLatch countDownLatch = new CountDownLatch(1);

        // Launch the activity
        ActivityScenario<ApiTestingActivity> scenario = ActivityScenario.launch(ApiTestingActivity.class);

        scenario.onActivity(activity -> {
            activity.getDiscover(movieList, countDownLatch);
        });



        //wait for latch to release
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(20, movieList.size());

    }

}
