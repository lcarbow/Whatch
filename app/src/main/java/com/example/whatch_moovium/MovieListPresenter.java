package com.example.whatch_moovium;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieListPresenter implements Contract.MovieListPresenter, Contract.ModelView.OnFinishedListener,Interfaces.apiDiscoverCallback, Interfaces.apiBackdropCallback, Interfaces.apiPosterCallback{
    private Contract.LandingViewGenre landingPageView;
    private Contract.ModelView model;

    ApiInterface myAPI_Interface;
    List<Movie> movieList;
    List<Model> itemList;
    List<String> genreList;

    //besser?
    int index;

    public MovieListPresenter(Contract.LandingViewGenre landingPageView) {
        this.landingPageView = landingPageView;
    }

    @Override
    public void getMovieListFromApi() {

            myAPI_Interface = new ApiInterface(landingPageView.getContext());

            List<Integer> providerList = new ArrayList<>();
            providerList.add(8);
            providerList.add(337);
            myAPI_Interface.getDiscover("popularity.desc", true, providerList, this);
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
    public void receiveDiscover(List<Movie> filteredMovieList) {
        Collections.shuffle(filteredMovieList);
        movieList = filteredMovieList;

        itemList = new ArrayList<>();

        genreList= new ArrayList<>();
        genreList.add("Komödie");
        genreList.add("Drama");
        genreList.add("Action");
        genreList.add("Fantasy");
        genreList.add("Familie");



        for (int i = 0; i < movieList.size(); i++){

            myAPI_Interface.getPoster(movieList.get(i).getPoster(), this);


        }
        for (int i = 0; i < genreList.size(); i++){

            model = new Model(genreList.get(i).toString(),movieList);

            itemList.add(new Model(genreList.get(i).toString(), movieList));


        }


    }

    @Override
    public void receivePoster(Bitmap img) {


        //Muss ausgeführt werden, noch bevor Seite geladen ist.
            movieList.get(model.showIndex()).setPosterBitmap(img);
            model.nextIndex();

        if (model.showIndex() == movieList.size()) {
            landingPageView.setAdapter(itemList);

        }

        //movieSuggestion.setPosterImage(img);
    }

    @Override
    public void receiveBackdrop(Bitmap img) {

    }

    @Override
    public void onFinished(Movie movie) {

    }
}
