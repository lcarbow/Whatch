package com.example.whatch_moovium;

import android.graphics.Bitmap;
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
