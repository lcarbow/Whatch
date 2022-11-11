package com.example.whatch_moovium;

import android.content.Intent;
import android.util.Log;

import java.util.List;


public class Presenter implements Contract.Presenter, Contract.Model.OnFinishedListener, API_Interface.apiInterfaceCallback {

    // creating object of View Interface
    private Contract.LandingView landingPageView;
    private Contract.MovieView movieSuggestion;

    // creating object of Model Interface
    private Contract.Model model;

    // instantiating the objects of View and Model Interface
    public Presenter(Contract.LandingView landingPageView, Contract.Model model) {
        this.landingPageView = landingPageView;
        this.model = model;
    }

    public Presenter(Contract.MovieView movieSuggestion, Contract.Model model) {
        this.movieSuggestion = movieSuggestion;
        this.model = model;
    }


    @Override
    public void onFinished(Movie movie) {
        if (landingPageView != null) {

            Log.i("userdebug", movie.getTitle());
            movieSuggestion.setTitle(movie.getTitle());
            movieSuggestion.setDescription(movie.getDescription());
            movieSuggestion.setRating(String.format("%.1f", (movie.getRating()*10)) + "% Benutzerbewertung");
            movieSuggestion.setGenre(movie.getGenre());
            movieSuggestion.setStreaming("Als Stream verfügbar auf " + movie.getStreaming());
            /*
            API_Interface myAPI_Interface = new API_Interface(movieSuggestion.getContext());
            myAPI_Interface.setCallback(this);

            myAPI_Interface.getRandom();


             */

            //mainView.setMovie(movie);
            //mainView.hideProgress();
        }
    }



    @Override
    public void onButtonClick() {
        if (landingPageView != null) {
            Intent i = new Intent(landingPageView.getContext(),MovieSuggestion.class);
            landingPageView.getContext().startActivity(i);


            //shared Prefs @Lasse
            //startActivity(i);
            //mainView.showProgress();
            // to MovieSuggestion
        }
        model.getNextMovie(this);
    }

    @Override
    public void displayMovie(List<Movie> movieList) {
        movieSuggestion.setTitle(movieList.get(0).getTitle());
        movieSuggestion.setDescription(movieList.get(0).getDescription());
        movieSuggestion.setRating(String.format("%.1f", (movieList.get(0).getRating()*10)) + "% Benutzerbewertung");
        movieSuggestion.setGenre(movieList.get(0).getGenre());
        movieSuggestion.setStreaming("Als Stream verfügbar auf " + movieList.get(0).getStreaming());
    }

    /*
    @Override
    public void onDestroy() {
        mainView = null;
    }

     */
}
