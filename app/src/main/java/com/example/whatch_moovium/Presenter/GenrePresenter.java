package com.example.whatch_moovium.Presenter;

import android.util.Log;

import com.example.whatch_moovium.API_Interface.ApiInterface;
import com.example.whatch_moovium.API_Interface.Interfaces;
import com.example.whatch_moovium.Aufraeumen.Genre;
import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.Model.Model;
import com.example.whatch_moovium.Model.Movie;
import com.example.whatch_moovium.Model.StorageClass;

import java.util.Arrays;
import java.util.List;

public class GenrePresenter implements Contract.IGenrePresenter, Contract.IModelView.OnFinishedListener, Interfaces.apiAllCallback, Interfaces.apiGenreCallback{

    private Contract.ILandingViewGenre landingPageView;
    ApiInterface myAPI_Interface;
    ImageHelper imageHelper;

    //Für das Setzen der Bilder. Muss aber iwie anders gemacht werden
    // FIND ICH NÄMLICH KACKE UND UMSTÄNDLICH
    private int outerIndex;
    private int innerIndex;

    public GenrePresenter(Contract.ILandingViewGenre landingPageView) {
        this.landingPageView = landingPageView;
        myAPI_Interface = new ApiInterface(landingPageView.getContext());
        this.innerIndex = 0;
        this.outerIndex = 0;

    }
    public GenrePresenter() {
        myAPI_Interface = new ApiInterface(landingPageView.getContext());
        this.innerIndex = 0;
        this.outerIndex = 0;

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
    public void loadImages(int horizontal, int horizonalCount, int vertical, int verticalCount) {
        for (int i = horizontal; i < horizontal+horizonalCount; i++) {
            for(int j = vertical; j < vertical+verticalCount ; j ++) {
                imageHelper = new ImageHelper(i,j,landingPageView);
                imageHelper.addImageToMovie();

            }
        }
        StorageClass.getInstance().setReady(true);
    }


    @Override
    public void onFinished(Movie movie) {

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
