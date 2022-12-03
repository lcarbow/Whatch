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


    public ImageHelper(int innerIndex, int outerIndex, Contract.ILandingViewGenre landingPageView) {
        this.innerIndex = innerIndex;
        this.outerIndex = outerIndex;
        myAPI_Interface = new ApiInterface(landingPageView.getContext());

    }

    void addImageToMovie() {
        myAPI_Interface.getPoster(StorageClass.getInstance().getMyModelList().get(outerIndex).getArrayList().get(innerIndex).getPoster(),this);
        //myAPI_Interface.getPoster(movie.getPoster(),this);
    }

    @Override
    public void receivePoster(Bitmap img) {
        StorageClass.getInstance().getMyModelList().get(outerIndex).getArrayList().get(innerIndex).setPosterBitmap(img);
    }
}
