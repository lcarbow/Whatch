package com.example.whatch_moovium.Presenter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.whatch_moovium.API_Interface.ApiHandler;
import com.example.whatch_moovium.API_Interface.Interfaces;
import com.example.whatch_moovium.Genre;
import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.Model.Model;
import com.example.whatch_moovium.Model.StorageClass;
import com.example.whatch_moovium.View.MovieSuggestion;

import java.util.List;

public class GenrePresenter implements Contract.IGenrePresenter, Interfaces.apiAllCallback, Interfaces.apiGenreCallback{

    private Contract.ILandingViewGenre landingPageView;
    private Contract.IImageLoader imageLoader;
    private ApiHandler myAPI_Interface;

//

    public GenrePresenter(Contract.ILandingViewGenre landingPageView) {
        this.landingPageView = landingPageView;
        myAPI_Interface = new ApiHandler(landingPageView.getContext());
        imageLoader = new ImageLoader(landingPageView.getContext());


    }


    @Override
    public void getMovieListFromApi() {

        StorageClass.getInstance().resetSettingForGenreList();
        myAPI_Interface.getGenres(this);
        myAPI_Interface.getAll("popularity.desc", true,StorageClass.getInstance().getProviderList(),this);



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


        landingPageView.setAdapter();


    }


    @Override
    public void receiveGenres(List<Genre> genres) {
        for (Genre g : genres) {
            StorageClass.getInstance().getGenreList().add(g.getName());
        }
    }


    @Override
    public void setImageViewForLoader(ImageView imageView) {
        imageLoader.setImageView(imageView);
    }

    @Override
    public void LoadImagesFromImageLoader(String imgPath) {imageLoader.loadImages(imgPath);}

}
