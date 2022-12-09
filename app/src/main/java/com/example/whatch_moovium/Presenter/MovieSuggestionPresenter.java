package com.example.whatch_moovium.Presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.example.whatch_moovium.API_Interface.ApiInterface;
import com.example.whatch_moovium.API_Interface.Interfaces;
import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.DatabaseHandler;
import com.example.whatch_moovium.Model.Movie;
import com.example.whatch_moovium.Model.StorageClass;


public class MovieSuggestionPresenter implements Contract.IMovieSuggestionPresenter, Contract.IModelView.OnFinishedListener, Interfaces.apiPosterCallback, Interfaces.apiWatchproviderCallback {

    // creating object of View Interface
    private Contract.IMovieView movieSuggestion;

    private ApiInterface myAPI_Interface;
    DatabaseHandler databaseHandler;

    // instantiating the objects of View Interface
    public MovieSuggestionPresenter(Contract.IMovieView movieSuggestion) {
        this.movieSuggestion = movieSuggestion;
        this.databaseHandler = new DatabaseHandler(movieSuggestion.getContext());
        this.myAPI_Interface = new ApiInterface(movieSuggestion.getContext());
    }

    @Override
    public void onPageLoaded() {
        StorageClass.getInstance().getMyModel().getThisMovie(this);
        //Log.i("architectureLog", String.valueOf(StorageClass.getInstance().getMyModel().showIndex()));

        for (Integer i : StorageClass.getInstance().getProviderList()) {
            //Log.i("providerLog", i.toString());
        }

    }


    @Override
    public void onFinished(Movie movie) {

        StorageClass.getInstance().setActualMovie(movie);
        myAPI_Interface.getPoster(movie.getPoster(), this);
        myAPI_Interface.getWatchprovider(movie, this);
        movieSuggestion.setTitle(movie.getTitle());
        movieSuggestion.setDescription(movie.getDescription());
        movieSuggestion.setRating(String.format("%.1f", (movie.getRating()*10)) + "% Benutzerbewertung");
        movieSuggestion.setGenre(movie.getGenre());
        //movieSuggestion.setStreaming("Als Stream verfügbar auf " + movie.getStreaming());
    }

    @Override
    public void receivePoster(Bitmap img) {

        movieSuggestion.setPosterImage(img);

    }

    @Override
    public void onButtonAddClick() {
        if(databaseHandler.CheckIfExist("watchlist", StorageClass.getInstance().getActualMovie().getId())){
            Toast.makeText(movieSuggestion.getContext(),
                    "Film ist bereits auf der Watchlist!", Toast.LENGTH_SHORT).show();
        }
        else {
            databaseHandler.addWatchlistMovie(StorageClass.getInstance().getActualMovie().getId());
            Toast.makeText(movieSuggestion.getContext(),
                    "Zur Watchlist hinzugefügt!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onButtonDeleteClick(){
        databaseHandler.delWatchlistMovie(StorageClass.getInstance().getActualMovie().getId());
        Toast.makeText(movieSuggestion.getContext(),
                "Aus Watchlist entfernt!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(movieSuggestion.getContext(),
                    "Film ist bereits auf der Seenlist!", Toast.LENGTH_SHORT).show();
        }
        else {
            databaseHandler.addSeenlistMovie(StorageClass.getInstance().getActualMovie().getId());
            Toast.makeText(movieSuggestion.getContext(),
                    "Zur Gesehenlist hinzugefügt!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onButtonNextClick() {
        StorageClass.getInstance().getMyModel().getNextMovie(this);

    }

    @Override
    public void onButtonBeforeClick() {
        StorageClass.getInstance().getMyModel().getBeforeMovie(this);

    }


    @Override
    public void receiveWatchprovider(Movie movie) {
        movieSuggestion.setStreaming("Als Stream verfügbar auf " + movie.getStreaming());
    }
}
