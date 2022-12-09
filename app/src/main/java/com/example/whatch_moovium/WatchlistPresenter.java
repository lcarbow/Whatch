package com.example.whatch_moovium;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import com.example.whatch_moovium.API_Interface.*;
import com.example.whatch_moovium.Model.Model;
import com.example.whatch_moovium.Model.Movie;
import com.example.whatch_moovium.View.MovieSuggestion;

import java.util.ArrayList;
import java.util.List;

public class WatchlistPresenter implements Contract.WatchlistPresenter, Contract.IModelView.OnFinishedListener,Interfaces.apiWatchlistCallback, Interfaces.apiBackdropCallback, Interfaces.apiPosterCallback{
    private Contract.LandingViewWatchlist landingPageWatchlist;
    private Contract.IModelView model;

    ApiInterface myApiInterface;
    DatabaseHandler dbHandler;
    List<Movie> movieList;
    List<Model> itemList;

    int index;

    public WatchlistPresenter(Contract.LandingViewWatchlist landingPageWatchlist){
        this.landingPageWatchlist = landingPageWatchlist;
    }

    @Override
    public void getMovieListFromApi() {

        myApiInterface = new ApiInterface(landingPageWatchlist.getContext());
        List<Integer> watchList = new ArrayList<>();
        watchList = dbHandler.getWatchlist();

        myApiInterface.getWatchlist(watchList, this);
    }

    @Override
    public void onButtonClick(){
        if (landingPageWatchlist != null) {
            Intent i = new Intent(landingPageWatchlist.getContext(), MovieSuggestion.class);
            landingPageWatchlist.getContext().startActivity(i);
            Log.i("userdebug","Umleitung zur MovieSugg");

        }
    }

    @Override
    public void receiveWatchlist(List<Movie> watchList){
        movieList = watchList;

    }

    @Override
    public void receivePoster(Bitmap img){

        /* NOPE!
        movieList.get(model.showIndex()).setPosterBitmap(img);
        model.nextIndex();

        if (model.showIndex() == movieList.size()){
            landingPageWatchlist.setAdapter(itemList);
        }

         */
    }

    @Override
    public void receiveBackdrop(Bitmap img){

    }

    @Override
    public void onFinished(Movie movie){

    }
}
