package com.example.whatch_moovium;

import androidx.test.core.app.ActivityScenario;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ApiInterfaceTest {



    @Test
    public void testGetDiscover() {

        // Launch the activity
        ActivityScenario<ApiTestingActivity> scenario = ActivityScenario.launch(ApiTestingActivity.class);

        scenario.onActivity(activity -> {
            activity.getDiscover();
        });


        Assert.assertEquals("", "");

    }



}
