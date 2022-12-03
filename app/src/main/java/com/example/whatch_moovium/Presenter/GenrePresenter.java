package com.example.whatch_moovium.Presenter;

import android.graphics.Bitmap;
import android.location.GnssMeasurementsEvent;
import android.util.Log;

import com.example.whatch_moovium.API_Interface.ApiInterface;
import com.example.whatch_moovium.API_Interface.Interfaces;
import com.example.whatch_moovium.Aufraeumen.Genre;
import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.Model.Model;
import com.example.whatch_moovium.Model.Movie;
import com.example.whatch_moovium.Model.StorageClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GenrePresenter implements Contract.IGenrePresenter, Contract.IModelView.OnFinishedListener, Interfaces.apiDiscoverCallback,  Interfaces.apiPosterCallback, Interfaces.apiAllCallback, Interfaces.apiGenreCallback{
    private Contract.ILandingViewGenre landingPageView;
    //private Contract.IModelView model;

    ApiInterface myAPI_Interface;

    //StorageClass.model
    //List<Movie> movieList;

    //private Map<String, Model> itemList;
    List<Model> itemList;
    List<String> genreList;

    //besser?
    private int outerIndex;
    private int innerIndex;
    private int testIndex = 0;



    public GenrePresenter(Contract.ILandingViewGenre landingPageView) {
        this.landingPageView = landingPageView;
        myAPI_Interface = new ApiInterface(landingPageView.getContext());
        this.itemList = new ArrayList<>();
        this.genreList = new ArrayList<>();
        this.innerIndex = 0;
        this.outerIndex = 0;

    }

    @Override
    public void getMovieListFromApi() {
        StorageClass.getInstance().setProviderList(Arrays.asList(8,337));
        myAPI_Interface.getGenres(this);
        myAPI_Interface.getAll("popularity.desc", true,StorageClass.getInstance().getProviderList(),this);
        //myAPI_Interface.getDiscover("popularity.desc", true, StorageClass.getInstance().getProviderList(), this);
    }


    @Override
    public void receiveDiscover(List<Movie> filteredMovieList) {

        //TODO @Alex
        //Das geht iwie nur mit nem Callback bzw er braucht n bisschen Zeit..?!
        //Uuuund er stürzt manchmal ab :(
        //Ne Idee?!
        landingPageView.setAdapter(itemList);


    }

    @Override
    public void onFinished(Movie movie) {

    }

    @Override
    public void receiveAll(List<List> list) {

        for (List modelList: list) {
            StorageClass.getInstance().addMyModelList(new Model(modelList));
        }

        for (int i = 0; i < StorageClass.getInstance().getMyModelList().size(); i++){
            itemList.add(new Model(genreList.get(i), StorageClass.getInstance().getMyModelList().get(i).getArrayList()));
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                Log.i("neuerLog", "testIndex = " + testIndex);
                myAPI_Interface.getPoster(StorageClass.getInstance().getMyModelList().get(i).getArrayList().get(j).getPoster(),this);
                testIndex++;
            }
        }

    }


    @Override
    public void receiveGenres(List<Genre> genres) {
        for (Genre g : genres) {
            genreList.add(g.getName());
        }

    }


    @Override
    public void receivePoster(Bitmap img) {
        StorageClass.getInstance().getMyModelList().get(outerIndex).getArrayList().get(innerIndex).setPosterBitmap(img);
        if(innerIndex <= 4) {
            innerIndex++;
        }
        if (innerIndex >= 4) {
            outerIndex++;
            innerIndex = 0;

        }
        Log.i("neuerLog", "innerIndex= " + innerIndex + " outerIndex= " + outerIndex);
        if (outerIndex >= 3) {
            landingPageView.setAdapter(itemList);

        }
    }

}
