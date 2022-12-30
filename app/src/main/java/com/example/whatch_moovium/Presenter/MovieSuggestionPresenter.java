package com.example.whatch_moovium.Presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.whatch_moovium.API_Interface.ApiInterface;
import com.example.whatch_moovium.API_Interface.Interfaces;
import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.DatabaseHandler;
import com.example.whatch_moovium.Model.Movie;
import com.example.whatch_moovium.Model.StorageClass;


public class MovieSuggestionPresenter implements Contract.IMovieSuggestionPresenter, Contract.IModelView.OnFinishedListener, Interfaces.apiPosterCallback {

    // creating object of View Interface
    private Contract.IMovieView movieSuggestion;

    private ApiInterface myAPI_Interface;
    DatabaseHandler databaseHandler;

    Button b_add;

    // instantiating the objects of View Interface
    public MovieSuggestionPresenter(Contract.IMovieView movieSuggestion) {
        this.movieSuggestion = movieSuggestion;
        this.databaseHandler = new DatabaseHandler(movieSuggestion.getContext());
        this.myAPI_Interface = new ApiInterface(movieSuggestion.getContext());
    }

    @Override
    public void onPageLoaded() {
        StorageClass.getInstance().getMyModel().getThisMovie(this);

        /*for (Integer i : StorageClass.getInstance().getProviderList()) {
        }*/

    }


    @Override
    public void onFinished(Movie movie) {

        StorageClass.getInstance().setActualMovie(movie);
        myAPI_Interface.getPoster(movie.getPoster(), this);
        movieSuggestion.setTitle(movie.getTitle());
        movieSuggestion.setDescription(movie.getDescription());
        movieSuggestion.setRating(String.format("%.1f", (movie.getRating()*10)) + "% Benutzerbewertung");
        movieSuggestion.setGenre(movie.getGenre());


        movieSuggestion.setStreaming("Als Stream verfügbar auf " + movie.getStreaming());
    }

    @Override
    public void receivePoster(Bitmap img) {

        movieSuggestion.setPosterImage(img);

    }

    @Override
    public void onButtonAddClick() {

            databaseHandler.addWatchlistMovie(StorageClass.getInstance().getActualMovie().getId());
            Toast.makeText(movieSuggestion.getContext(),
                    "Zur Watchlist hinzugefügt!", Toast.LENGTH_SHORT).show();
            movieSuggestion.setButtonAddVisibility(4);
            movieSuggestion.setButtonDeleteVisibility(0);

    }

    @Override
    public void onButtonDeleteClick(){
        databaseHandler.delWatchlistMovie(StorageClass.getInstance().getActualMovie().getId());
        Toast.makeText(movieSuggestion.getContext(),
                "Aus Watchlist entfernt!", Toast.LENGTH_SHORT).show();
        movieSuggestion.setButtonAddVisibility(0);
        movieSuggestion.setButtonDeleteVisibility(4);

    }

    @Override
    public void onButtonShareClick() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Schau, welchen coolen Film ich gefunden habe: " + StorageClass.getInstance().getActualMovie().getTitle());
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        movieSuggestion.getContext().startActivity(shareIntent);
    }

    @Override
    public void onButtonSeenClick() {
        if(databaseHandler.CheckIfExist("seenlist", StorageClass.getInstance().getActualMovie().getId())){
            databaseHandler.delSeenlistMovie(StorageClass.getInstance().getActualMovie().getId());
            Toast.makeText(movieSuggestion.getContext(),
                    "Film aus Seenlist entfernt!", Toast.LENGTH_SHORT).show();
            movieSuggestion.unsetSeenButtonColor();
        }
        else {
            databaseHandler.addSeenlistMovie(StorageClass.getInstance().getActualMovie().getId());
            Toast.makeText(movieSuggestion.getContext(),
                    "Zur Gesehenlist hinzugefügt!", Toast.LENGTH_SHORT).show();
            movieSuggestion.setSeenButtonColor();
        }
    }

    public void onButtonNextClick() {
        StorageClass.getInstance().getMyModel().getNextMovie(this);
        exist();
    }

    @Override
    public void onButtonBeforeClick() {
        StorageClass.getInstance().getMyModel().getBeforeMovie(this);
        exist();

    }



    @Override
    public void exist(){
        if(databaseHandler.CheckIfExist("watchlist", StorageClass.getInstance().getActualMovie().getId())){
            movieSuggestion.setButtonAddVisibility(4);
            movieSuggestion.setButtonDeleteVisibility(0);
        }
        else{
            movieSuggestion.setButtonAddVisibility(0);
            movieSuggestion.setButtonDeleteVisibility(4);
        }

        if(databaseHandler.CheckIfExist("seenlist", StorageClass.getInstance().getActualMovie().getId())){
            movieSuggestion.setSeenButtonColor();
        }
        else {
            movieSuggestion.unsetSeenButtonColor();
        }
    }
}
