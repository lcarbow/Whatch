package com.example.whatch_moovium.Presenter;

import android.content.Intent;

import com.example.whatch_moovium.API_Interface.ApiInterface;
import com.example.whatch_moovium.API_Interface.Interfaces;
import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.Model.Model;
import com.example.whatch_moovium.Model.Movie;
import com.example.whatch_moovium.Model.StorageClass;
import com.example.whatch_moovium.View.MovieSuggestion;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SurprisePresenter implements Contract.ISurprisePresenter, Interfaces.apiDiscoverCallback{

    private Contract.ILandingViewSurprise landingPageView;
    private ApiInterface myAPI_Interface;

    public SurprisePresenter(Contract.ILandingViewSurprise landingPageView) {
        this.landingPageView = landingPageView;
        myAPI_Interface = new ApiInterface(landingPageView.getContext());

    }

    @Override
    public void onButtonClick() {
        if (landingPageView != null) {
            Intent i = new Intent(landingPageView.getContext(), MovieSuggestion.class);
            landingPageView.getContext().startActivity(i);
        }
    }

    @Override
    public void getMovieListFromApi() {
        //TODO @Nadine: Zum speichern der IDs
        StorageClass.getInstance().setProviderList(Arrays.asList(8,337));

        myAPI_Interface.getDiscover("popularity.desc", true, StorageClass.getInstance().getProviderList(), this);

    }

    @Override
    public void receiveDiscover(List<Movie> filteredMovieList) {
        Collections.shuffle(filteredMovieList);
        StorageClass.getInstance().setMyModel(new Model(filteredMovieList));
    }



}
