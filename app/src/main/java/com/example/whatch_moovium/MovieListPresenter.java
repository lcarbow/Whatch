package com.example.whatch_moovium;

import androidx.annotation.NonNull;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieListPresenter implements Contract.MovieListPresenter, Interfaces.apiDiscoverCallback, Interfaces.apiBackdropCallback, Interfaces.apiPosterCallback{
    private Contract.LandingViewGenre landingPageView;

    ApiInterface myAPI_Interface;
    List<Movie> movieList;

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
    public void receiveDiscover(List<Movie> filteredMovieList) {
        Collections.shuffle(filteredMovieList);
        movieList = filteredMovieList;

        List<LandingPage_Genres_ModelParent> itemList = new ArrayList<>();

        ArrayList genreList= new ArrayList<String>();
        genreList.add("Komödie");
        genreList.add("Drama");
        genreList.add("Action");
        genreList.add("Fantasy");
        genreList.add("Familie");



        for (int i = 0; i < movieList.size(); i++){

            myAPI_Interface.getPoster(movieList.get(i).getPoster(), this);


        }
        for (int i = 0; i < genreList.size(); i++){

            itemList.add(new LandingPage_Genres_ModelParent(genreList.get(i).toString(), movieList));


        }


        landingPageView.setAdapter(itemList);

        //4. neue Model Klasse erstellen mit den neuen Filmen
        //model = new Model(filteredMovieList);
        //ChildItemList(filteredMovieList);
        //5. Funktion von model aufrufen, in der gewartet wird bis der listener ready ist
        //model.getNextMovie(this);
    }

    @Override
    public void receivePoster(Bitmap img) {
            //Muss ausgeführt werden, noch bevor Seite geladen ist.
            movieList.get(index).setPosterBitmap(img);
            index++;

        //movieSuggestion.setPosterImage(img);
    }

    @Override
    public void receiveBackdrop(Bitmap img) {

    }

}
