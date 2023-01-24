package com.example.whatch_moovium;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.whatch_moovium.API_Interface.WatchProviderConverter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;


@RunWith(AndroidJUnit4.class)
public class WatchProviderConverterTest {

    private static final String API_KEY = "f862a1abef6de0d1ca20c51abb9f51ab";

    // Context of the app under test.
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();


    @Test
    public void testGetWatchProviderName() throws InterruptedException {
        WatchProviderConverter converter = WatchProviderConverter.getInstance(context, API_KEY);
        String providerName = converter.getWatchProviderName(8);
        Assert.assertEquals("Netflix", providerName);
    }

    @Test
    public void testGetWatchProviderId() throws InterruptedException {
        WatchProviderConverter converter = WatchProviderConverter.getInstance(context, API_KEY);
        Integer providerId = converter.getWatchProviderId("Netflix");
        Assert.assertEquals(8, providerId.intValue());
    }

    @Test
    public void testStringListToIds() throws InterruptedException {
        WatchProviderConverter converter = WatchProviderConverter.getInstance(context, API_KEY);

        List<String> providerNames = new ArrayList<>();
        providerNames.add("Netflix");
        providerNames.add("Amazon Prime Video");

        List<Integer> providerIds = converter.stringListToIDs(providerNames);

        Assert.assertEquals(2, providerIds.size());
        Assert.assertEquals(8, providerIds.get(0).intValue());
        Assert.assertEquals(9, providerIds.get(1).intValue());
    }
}

