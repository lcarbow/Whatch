package com.example.whatch_moovium;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.whatch_moovium.API_Interface.ApiRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ApiRequestTest {

    @Test
    public void testApiRequest() throws InterruptedException, JSONException {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        RequestQueue queue = Volley.newRequestQueue(appContext);
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=f862a1abef6de0d1ca20c51abb9f51ab";

        ApiRequest apiRequest = new ApiRequest(url, queue);
        JSONObject jsonObject = apiRequest.getJSON();

        Assert.assertNotNull(jsonObject);
        Assert.assertTrue(jsonObject.has("results"));
        JSONArray results = jsonObject.getJSONArray("results");
        Assert.assertTrue(results.length() > 0);

    }

}
