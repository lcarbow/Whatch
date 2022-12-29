package com.example.whatch_moovium.API_Interface;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class WatchProviderConverter {
    private Context context;
    private String apiKey;
    private Map<Integer, String> watchProviders;
    private CountDownLatch countDownLatch;

    public WatchProviderConverter(Context context, String apiKey) {
        this.context = context;
        this.apiKey = apiKey;
        watchProviders = new HashMap<>();
        countDownLatch = new CountDownLatch(1);
        refreshWatchProviders();
    }

    private void refreshWatchProviders() {
        String url = "https://api.themoviedb.org/3/watch/providers/movie?api_key=" + apiKey + "&language=de-DE&watch_region=DE";
        RequestQueue queue = Volley.newRequestQueue(context);
        //Log.i("Alex", "refresh");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Log.i("Alex", "response received");
                try {
                    JSONArray watchProvidersData = response.getJSONArray("results");
                    for (int i = 0; i < watchProvidersData.length(); i++) {
                        JSONObject provider = watchProvidersData.getJSONObject(i);
                        int id = provider.getInt("provider_id");
                        String name = provider.getString("provider_name");
                        watchProviders.put(id, name);
                        //Log.i("Alex", "Name, id: " + id + " " + name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("Alex", "Error");
                }
                countDownLatch.countDown();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                countDownLatch.countDown();
            }
        });
        queue.add(request);
    }

    public String getWatchProviderName(int watchProviderId) throws InterruptedException {
        countDownLatch.await();
        return watchProviders.get(watchProviderId);
    }

    public Integer getWatchProviderId(String watchProviderName) throws InterruptedException {
        countDownLatch.await();
        for (Map.Entry<Integer, String> entry : watchProviders.entrySet()) {
            if (entry.getValue().equals(watchProviderName)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
