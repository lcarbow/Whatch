package com.example.whatch_moovium.API_Interface;

import android.graphics.Bitmap;

import com.example.whatch_moovium.Genre;
import com.example.whatch_moovium.Model.Movie;

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

    public interface apiAllCallback {
        void receiveAll(List<List> list);
    }

    public interface apiGenreCallback {
        void receiveGenres(List<Genre> genres);
    }

    public interface apiWatchlistCallback {
        void receiveWatchlist(List<Movie> watchlist);
    }

    public interface apiWatchproviderCallback {
        void receiveWatchprovider(Movie movie);
    }

    public interface apiSimilarCallback {
        void receiveSimilar(List<Movie> similarList);
    }

}
