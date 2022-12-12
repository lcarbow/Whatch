package com.example.whatch_moovium.Presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.whatch_moovium.API_Interface.ApiInterface;
import com.example.whatch_moovium.API_Interface.Interfaces;
import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.Model.Model;
import com.example.whatch_moovium.Model.Movie;
import com.example.whatch_moovium.Model.StorageClass;
import com.example.whatch_moovium.View.LandingPage_MoodSuggestion;
import com.example.whatch_moovium.View.MovieSuggestion;

import java.util.Collections;
import java.util.List;

public class MoodPresenter implements Contract.IMoodPresenter, Interfaces.apiDiscoverCallback{

    private Contract.ILandingViewMood landingPageView;
    private ApiInterface myAPI_Interface;



    public MoodPresenter(Contract.ILandingViewMood landingPageView) {
        this.landingPageView = landingPageView;
        myAPI_Interface = new ApiInterface(landingPageView.getContext());
    }

    @Override
    public void onPageLoaded() {
        //TODO: Wenn am heutigen Tag bereits ein Gefühl gewählt wurde, direkt zur MovieSugg bevor diese Seite geladen wird.
        //TODO: if dailyClicked [sharedPrefs]
            //getSimilarMovieListFromApi()
            //toMovieSuggestion()
        //TODO else:
            getRandomMovieListFromApi();
    }

    @Override
    public void onEmojiClick() {
        //TODO: in DB nach gewählter mood suchen
        //TODO: if DB.gewählte_mood.size < 3:
            toMoodSuggestion();
        //TODO: else:
            //getSimilarMovieListFromApi()
            //toMovieSuggestion();

    }

    @Override
    public void getRandomMovieListFromApi() {
        //TODO: API_Request:Random für vorzuschlagene Filme
        //TODO: Bitte aber mal was anderes als wir bei SurpriseMovie haben.
        myAPI_Interface.getDiscover("popularity.desc", true, StorageClass.getInstance().getProviderList(), this);
    }



    @Override
    public void getSimilarMovieListFromApi() {
        //TODO: API_Request:getSimilar mit bereits in DB enthaltenen Filmen
    }

    @Override
    public void receiveDiscover(List<Movie> filteredMovieList) {
        Collections.shuffle(filteredMovieList);
        StorageClass.getInstance().setMyModel(new Model(filteredMovieList));
    }

    @Override
    public void toMovieSuggestion() {
        if (landingPageView != null) {
            Intent i = new Intent(landingPageView.getContext(), MovieSuggestion.class);
            landingPageView.getContext().startActivity(i);
        }
    }

    @Override
    public void toMoodSuggestion() {
        Intent i = new Intent(landingPageView.getContext(), LandingPage_MoodSuggestion.class);
        landingPageView.getContext().startActivity(i);
    }





}
