package com.example.whatch_moovium.Presenter;

import android.graphics.Bitmap;

import com.example.whatch_moovium.API_Interface.ApiInterface;
import com.example.whatch_moovium.API_Interface.Interfaces;
import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.Model.StorageClass;

public class ImageHelperVertical implements Interfaces.apiPosterCallback{

    ApiInterface myAPI_Interface;
    int innerIndex;
    int outerIndex;
    Contract.ILandingViewGenre landingPageView;


    public ImageHelperVertical(int innerIndex, int outerIndex, Contract.ILandingViewGenre landingPageView) {
        this.innerIndex = innerIndex;
        this.outerIndex = outerIndex;
        this.landingPageView = landingPageView;
        myAPI_Interface = new ApiInterface(landingPageView.getContext());

    }

    void addImageToMovie() {
        if(StorageClass.getInstance().getMyModelList().get(outerIndex).getArrayList().get(innerIndex).isImageLoaded() == false) {
            myAPI_Interface.getPoster(StorageClass.getInstance().getMyModelList().get(outerIndex).getArrayList().get(innerIndex).getPoster(),this);
        }
    }

    @Override
    public void receivePoster(Bitmap img) {
        StorageClass.getInstance().getMyModelList().get(outerIndex).getArrayList().get(innerIndex).setPosterBitmap(img);
        StorageClass.getInstance().getMyModelList().get(outerIndex).getArrayList().get(innerIndex).setImageLoaded(true);
        landingPageView.dataChange();

    }
}
