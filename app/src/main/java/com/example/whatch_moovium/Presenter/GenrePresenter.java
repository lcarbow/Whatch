package com.example.whatch_moovium.Presenter;

import android.content.Intent;
import android.view.View;

import com.example.whatch_moovium.API_Interface.ApiInterface;
import com.example.whatch_moovium.API_Interface.Interfaces;
import com.example.whatch_moovium.Genre;
import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.Model.Model;
import com.example.whatch_moovium.Model.Movie;
import com.example.whatch_moovium.Model.StorageClass;
import com.example.whatch_moovium.View.MovieSuggestion;

import java.util.Arrays;
import java.util.List;

public class GenrePresenter implements Contract.IGenrePresenter, Interfaces.apiAllCallback, Interfaces.apiGenreCallback{

    private Contract.ILandingViewGenre landingPageView;
    ApiInterface myAPI_Interface;


    public GenrePresenter(Contract.ILandingViewGenre landingPageView) {
        this.landingPageView = landingPageView;
        myAPI_Interface = new ApiInterface(landingPageView.getContext());
    }

    @Override
    public void getMovieListFromApi() {
        //TODO @Nadine bei den Triggern setzen, dann kann das hier weg
        StorageClass.getInstance().setProviderList(Arrays.asList(8,337));

        if(StorageClass.getInstance().getItemList().size() == 0) {
            //Verhindert die lange Wartezeit, die Filme werden gespeichert.
            myAPI_Interface.getGenres(this);
            myAPI_Interface.getAll("popularity.desc", true,StorageClass.getInstance().getProviderList(),this);
        } else {
            landingPageView.setAdapter(StorageClass.getInstance().getItemList());

        }
    }

    @Override
    public void setMovieVertical(int position) {
        StorageClass.getInstance().setMyModel(StorageClass.getInstance().getMyModelList().get(position));
    }

    @Override
    public void onClickImage(View view, int adapterPosition) {
        Intent i = new Intent(view.getContext(), MovieSuggestion.class);
        view.getContext().startActivity(i);
        StorageClass.getInstance().getMyModel().setIndex(adapterPosition);
    }

    @Override
    public void receiveAll(List<List> list) {

        for (List modelList: list) {
            StorageClass.getInstance().addMyModelList(new Model(modelList));
        }
        //Zur ItemList hinzufügen
        for (int i = 0; i < StorageClass.getInstance().getGenreList().size(); i++){
            StorageClass.getInstance().getItemList().add(new Model(StorageClass.getInstance().getGenreList().get(i), StorageClass.getInstance().getMyModelList().get(i).getArrayList()));
        }


        landingPageView.setAdapter(StorageClass.getInstance().getItemList());


    }


    @Override
    public void receiveGenres(List<Genre> genres) {
        for (Genre g : genres) {
            StorageClass.getInstance().getGenreList().add(g.getName());
        }

    }



}
