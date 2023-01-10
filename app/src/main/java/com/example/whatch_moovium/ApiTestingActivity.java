package com.example.whatch_moovium;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whatch_moovium.API_Interface.ApiInterface;
import com.example.whatch_moovium.API_Interface.Interfaces;
import com.example.whatch_moovium.Model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ApiTestingActivity extends AppCompatActivity implements Interfaces.apiDiscoverCallback {

    private List<Movie> movieList;
    private CountDownLatch countDownLatch;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.i("AlexTesting", "Activity resumed");
    }

    public void getDiscover(List<Movie> movieList, CountDownLatch countDownLatch) {
        this.movieList = movieList;
        this.countDownLatch = countDownLatch;

        Log.i("AlexTesting", "getdiscover");
        ApiInterface apiInterface = new ApiInterface(ApiTestingActivity.this);

        List<String> providerList = new ArrayList<>();
        providerList.add("Netflix");
        providerList.add("Disney Plus");

        apiInterface.getDiscover("popularity.desc", true, providerList, this);
    }

    @Override
    public void receiveDiscover(List<Movie> filteredMovieList) {
        Log.i("AlexTesting", "getdiscoverCallback");
        //put filteresList into list
        for (Movie movie : filteredMovieList) {
            movieList.add(movie);
        }

        countDownLatch.countDown();
    }
}