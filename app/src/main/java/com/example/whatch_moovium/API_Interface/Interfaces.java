package com.example.whatch_moovium.API_Interface;

import android.graphics.Bitmap;

import com.example.whatch_moovium.Movie;

import java.util.List;

public class Interfaces {

    public interface apiDiscoverCallback {
        void receiveDiscover(List<Movie> filteredMovieList);
    }

    public interface apiPosterCallback {
        void receivePoster(Bitmap img);
    }

    public interface apiBackdropCallback {
        void receiveBackdrop(Bitmap img);
    }

}
