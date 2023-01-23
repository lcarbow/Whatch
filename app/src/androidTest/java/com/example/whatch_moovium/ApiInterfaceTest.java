package com.example.whatch_moovium;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.whatch_moovium.API_Interface.ApiHandler;
import com.example.whatch_moovium.API_Interface.Interfaces;
import com.example.whatch_moovium.Model.Movie;
import com.example.whatch_moovium.Presenter.BottomNavPresenter;
import com.example.whatch_moovium.Presenter.GenrePresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.AccessibleObject;
import java.util.List;

/*@RunWith(AndroidJUnit4.class)
public class ApiInterfaceTest implements Interfaces.apiDiscoverCallback {

    private static final String API_KEY = "f862a1abef6de0d1ca20c51abb9f51ab";

    // Context of the app
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();


    ApiInterface apiInterface;
    List<String> providers;

    @Before
    public void setup() {
        apiInterface = new ApiInterface(this);
        providers.add("Netflix");
        providers.add("Disney Plus");
    }

    @Test
    public void testDiscoverRequest() {

        apiInterface.getDiscover("popularity.desc", true, providers, this);

    }


    @Override
    public void receiveDiscover(List<Movie> movieList) {

        //check if list is filled
        Assert.assertTrue(movieList.size() > 0);

        //check if each movie has a title
        for (Movie movie : movieList) {
            Assert.assertTrue(movie.getTitle().length() > 0);
        }

    }
}*/

