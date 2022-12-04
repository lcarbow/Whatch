package com.example.whatch_moovium.Presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.whatch_moovium.API_Interface.ApiInterface;
import com.example.whatch_moovium.API_Interface.Interfaces;
import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.Model.Movie;
import com.example.whatch_moovium.Model.StorageClass;

public class ImageHelper implements Interfaces.apiPosterCallback{

    ApiInterface myAPI_Interface;
    int innerIndex;
    int outerIndex;
    Contract.ILandingViewGenre landingPageView;


    public ImageHelper(int innerIndex, int outerIndex, Contract.ILandingViewGenre landingPageView) {
        this.innerIndex = innerIndex;
        this.outerIndex = outerIndex;
        this.landingPageView = landingPageView;
        myAPI_Interface = new ApiInterface(landingPageView.getContext());

    }

    void addImageToMovie() {
        if(StorageClass.getInstance().getMyModelList().get(outerIndex).getArrayList().get(innerIndex).isImageLoaded() == false) {
            myAPI_Interface.getPoster(StorageClass.getInstance().getMyModelList().get(outerIndex).getArrayList().get(innerIndex).getPoster(),this);
        }
        //myAPI_Interface.getPoster(movie.getPoster(),this);
    }

    @Override
    public void receivePoster(Bitmap img) {
        StorageClass.getInstance().getMyModelList().get(outerIndex).getArrayList().get(innerIndex).setPosterBitmap(img);
        StorageClass.getInstance().getMyModelList().get(outerIndex).getArrayList().get(innerIndex).setImageLoaded(true);
        //TODO Der Horizontale Scroll setzt sich immer zurück nachdem die Bilder geladen wurden...
        landingPageView.dataChange();


    }
}
