package com.example.whatch_moovium;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.example.whatch_moovium.API_Interface.ApiInterface;
import com.example.whatch_moovium.API_Interface.Interfaces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Presenter implements Contract.Presenter, Contract.Model.OnFinishedListener, Interfaces.apiDiscoverCallback, Interfaces.apiBackdropCallback, Interfaces.apiPosterCallback {

    // creating object of View Interface
    private Contract.LandingView landingPageView;
    private Contract.MovieView movieSuggestion;

    // creating object of Model Interface
    private Contract.Model model;

    Movie actuallyMovie;
    ApiInterface myAPI_Interface;


    DatabaseHandler databaseHandler;

    // instantiating the objects of View Interface
    public Presenter(Contract.LandingView landingPageView) {
        this.landingPageView = landingPageView;
    }

    // instantiating the objects of View Interface
    public Presenter(Contract.MovieView movieSuggestion) {
        this.movieSuggestion = movieSuggestion;
        this.databaseHandler = new DatabaseHandler(movieSuggestion.getContext());
    }

    @Override
    public void onButtonClick() {
        if (landingPageView != null) {
            Intent i = new Intent(landingPageView.getContext(),MovieSuggestion.class);
            landingPageView.getContext().startActivity(i);
            Log.i("userdebug","Umleitung zur MovieSugg");

        }
    }

    @Override
    public void onButtonAddClick() {
        databaseHandler.addWatchlistMovie(actuallyMovie.getId());
        Toast.makeText(movieSuggestion.getContext(),
                "Zur Watchlist hinzugefügt!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onButtonShareClick() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Schau, welchen coolen Film ich gefunden habe: " + actuallyMovie.getTitle());
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        movieSuggestion.getContext().startActivity(shareIntent);
    }

    @Override
    public void onButtonSeenClick() {
        databaseHandler.addSeenlistMovie(actuallyMovie.getId());
        Toast.makeText(movieSuggestion.getContext(),
                "Zur Gesehenlist hinzugefügt!", Toast.LENGTH_SHORT).show();
    }

    public void onButtonNextClick() {
        model.getNextMovie(this);
    }

    @Override
    public void onButtonBeforeClick() {
        model.getBeforeMovie(this);
    }

    @Override
    public void getMovieListFromApi() {
        //1. wird bei oncreate von movieSugg ausgeführt
        if (movieSuggestion != null) {
            Log.i("userdebug", "2. getMoveListFromAPI");
            /*
            API_Interface myAPI_Interface = new API_Interface(movieSuggestion.getContext());
            myAPI_Interface.setCallback(this);
            myAPI_Interface.getRandom();

             */

            //make api interface
            myAPI_Interface = new ApiInterface(movieSuggestion.getContext());
            //set callback
            Log.i("userdebug", "3. getMoveListFromAPI");

            Log.i("userdebug", "4. getMoveListFromAPI");



            List<Integer> providerList = new ArrayList<>();
            providerList.add(8);
            providerList.add(337);
            myAPI_Interface.getDiscover("popularity.desc", true, providerList, this);
        }
    }



    @Override
    public void receiveDiscover(List<Movie> filteredMovieList) {
        //3. Komplette Liste Shufflen
        Collections.shuffle(filteredMovieList);
        //4. neue Model Klasse erstellen mit den neuen Filmen
        model = new Model(filteredMovieList);
        //5. Funktion von model aufrufen, in der gewartet wird bis der listener ready ist
        model.getNextMovie(this);
    }
    @Override
    public void onFinished(Movie movie) {
        //6. Setzen der Views
        this.actuallyMovie = movie;
        myAPI_Interface.getPoster(movie.getPoster(), this);

        movieSuggestion.setTitle(movie.getTitle());
        movieSuggestion.setDescription(movie.getDescription());
        movieSuggestion.setRating(String.format("%.1f", (movie.getRating()*10)) + "% Benutzerbewertung");
        movieSuggestion.setGenre(movie.getGenre());
        movieSuggestion.setStreaming("Als Stream verfügbar auf " + movie.getStreaming());
        //movieSuggestion.setPosterImage(movie.getPoster());
    }


    @Override
    public void receivePoster(Bitmap img) {
        //posterView.setImageBitmap(img);
        movieSuggestion.setPosterImage(img);

    }

    @Override
    public void receiveBackdrop(Bitmap img) {
    }



/*
    @Override
    //2. Api führt diese Funktion aus wenn MovieListe fertig
    public void displayMovie(List<Movie> movieList) {
        //3. Komplette Liste Shufflen
        Collections.shuffle(movieList);
        //4. neue Model Klasse erstellen mit den neuen Filmen
        model = new Model(movieList);
        //5. Funktion von model aufrufen, in der gewartet wird bis der listener ready ist
        model.getNextMovie(this);
    }

 */
}
