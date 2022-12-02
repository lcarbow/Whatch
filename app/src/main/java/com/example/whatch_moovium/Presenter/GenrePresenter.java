package com.example.whatch_moovium.Presenter;

import android.graphics.Bitmap;
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
    List<Movie> MovieListt;
    List<String> genreList;

    //besser?
    private int outerIndex;
    private int innerIndex = 0;



    public GenrePresenter(Contract.ILandingViewGenre landingPageView) {
        this.landingPageView = landingPageView;
        myAPI_Interface = new ApiInterface(landingPageView.getContext());
        this.itemList = new ArrayList<>();
        this.genreList = new ArrayList<>();
        this.MovieListt = new ArrayList<>();

        outerIndex = 0;

    }

    @Override
    public void getMovieListFromApi() {
        StorageClass.getInstance().setProviderList(Arrays.asList(8,337));
        myAPI_Interface.getGenres(this);
        //myAPI_Interface.getDiscover("popularity.desc", true, StorageClass.getInstance().getProviderList(), this);
        myAPI_Interface.getAll("popularity.desc", true,StorageClass.getInstance().getProviderList(),this);
    }


    @Override
    public void receiveDiscover(List<Movie> filteredMovieList) {

        Collections.shuffle(filteredMovieList);
        StorageClass.getInstance().setMyModel(new Model(filteredMovieList));

        for (int i = 0; i < StorageClass.getInstance().getMyModel().getArrayList().size(); i++){

            myAPI_Interface.getPoster(StorageClass.getInstance().getMyModel().getArrayList().get(i).getPoster(), this);


        }
        for (int i = 0; i < genreList.size(); i++){

            //model = new Model(genreList.get(i).toString(),StorageClass.getInstance().getMyModel().getArrayList());

            itemList.add(new Model(genreList.get(i).toString(), StorageClass.getInstance().getMyModel().getArrayList()));
            //itemList.put(genreList.get(i).toString(), StorageClass.getInstance().getMyModel());

        }


    }

    @Override
    public void receivePoster(Bitmap img) {

        //StorageClass.getInstance().getMyModelList().get(outerIndex).getArrayList().get(innerIndex).setPosterBitmap(img);

        //innerIndex++;
        //Log.i("architectureLog", "innerIndex: " + innerIndex);
/*
        if (innerIndex == 20) {
            innerIndex = 0;
            outerIndex++;
            //Log.i("architectureLog", "outerIndex: " + outerIndex);

        } if(outerIndex == 15) {
            landingPageView.setAdapter(itemList);

        }

 */
        landingPageView.setAdapter(itemList);


    }

    @Override
    public void onFinished(Movie movie) {

    }

    @Override
    public void receiveAll(List<List> list) {

        for (List modelList: list) {
            Model myModel = new Model(modelList);
            StorageClass.getInstance().addMyModelList(myModel);
        }

        for (int i = 0; i < StorageClass.getInstance().getMyModelList().size(); i++){
            //Log.i("architectureLog", "" + genreList.get(i));

            for (int j = 0; j < StorageClass.getInstance().getMyModelList().size(); j++) {
                //Log.i("architectureLog", "" + StorageClass.getInstance().getMyModelList().get(i).getArrayList().get(j).getTitle());

                myAPI_Interface.getPoster(StorageClass.getInstance().getMyModelList().get(0).getArrayList().get(0).getPoster(), this);
            }
            itemList.add(new Model(genreList.get(i), StorageClass.getInstance().getMyModelList().get(i).getArrayList()));

        }


    }


    @Override
    public void receiveGenres(List<Genre> genres) {
        for (Genre g : genres) {
            genreList.add(g.getName());
        }
    }
}
