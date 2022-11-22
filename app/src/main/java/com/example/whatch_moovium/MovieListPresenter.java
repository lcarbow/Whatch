package com.example.whatch_moovium;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieListPresenter implements Contract.MovieListPresenter, Contract.Model.OnFinishedListener, Interfaces.apiDiscoverCallback, Interfaces.apiBackdropCallback, Interfaces.apiPosterCallback{
    private Contract.LandingView landingPageView;

    // creating object of Model Interface
    private Contract.Model model;

    Movie actuallyMovie;
    ApiInterface myAPI_Interface;

    public MovieListPresenter(Contract.LandingView landingPageView) {
        this.landingPageView = landingPageView;
    }

    @Override
    public void getMovieListFromApi() {

            //make api interface
            myAPI_Interface = new ApiInterface(landingPageView.getContext());
            //set callback

            List<Integer> providerList = new ArrayList<>();
            providerList.add(8);
            providerList.add(337);
            myAPI_Interface.getDiscover("popularity.desc", true, providerList, this);
    }


    @Override
    public void receiveDiscover(List<Movie> filteredMovieList) {
        //3. Komplette Liste Shufflen
        Collections.shuffle(filteredMovieList);
        //4. neue Model Klasse erstellen mit den neuen Filmen
        model = new Model(filteredMovieList);
        //ChildItemList(filteredMovieList);
        //5. Funktion von model aufrufen, in der gewartet wird bis der listener ready ist
        model.getNextMovie(this);
        //landingPageView.childRecyc(filteredMovieList);
    }

    @Override
    public void receivePoster(Bitmap img) {
        //movieSuggestion.setPosterImage(img);
    }

    @Override
    public void receiveBackdrop(Bitmap img) {

    }

    @Override
    public void onFinished(Movie movie) {
        this.actuallyMovie = movie;
        myAPI_Interface.getPoster(movie.getPoster(), this);
    }
}
