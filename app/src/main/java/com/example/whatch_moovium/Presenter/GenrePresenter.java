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

public class GenrePresenter implements Contract.IGenrePresenter, Contract.IModelView.OnFinishedListener, Interfaces.apiPosterCallback, Interfaces.apiAllCallback, Interfaces.apiGenreCallback{
    private Contract.ILandingViewGenre landingPageView;

    ApiInterface myAPI_Interface;

    private int outerIndex;
    private int innerIndex;

    public GenrePresenter(Contract.ILandingViewGenre landingPageView) {
        this.landingPageView = landingPageView;
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
        //Bilder anfordern für die sichtbaren Views
        //Dauert zu lange alle zu holen
        //Vllt iwie eine OnScroll Funktion um die nächsten zu holen?
        //Bzw das er sie nach und nach holt, aber kp
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                myAPI_Interface.getPoster(StorageClass.getInstance().getMyModelList().get(i).getArrayList().get(j).getPoster(),this);
            }
        }
        //TODO ALEX: Adapter soll hier sein, stürzt dann aber ab!
        //landingPageView.setAdapter(itemList);


    }


    @Override
    public void receiveGenres(List<Genre> genres) {
        for (Genre g : genres) {
            StorageClass.getInstance().getGenreList().add(g.getName());
        }

    }


    @Override
    public void receivePoster(Bitmap img) {
        //Bilder in der Reihenfolge zu den Models hinzufügen
        StorageClass.getInstance().getMyModelList().get(outerIndex).getArrayList().get(innerIndex).setPosterBitmap(img);
        if(innerIndex <= 4) {
            innerIndex++;
        }
        if (innerIndex >= 4) {
            outerIndex++;
            innerIndex = 0;
        }
        //Erst wenn die Schleife durch ist Adapter setzen
        if (outerIndex >= 3) {
            landingPageView.setAdapter(StorageClass.getInstance().getItemList());

        }
    }

}
