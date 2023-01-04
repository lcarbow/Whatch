package com.example.whatch_moovium.Presenter;

import android.content.Intent;
import android.widget.ImageButton;

import com.example.whatch_moovium.API_Interface.ApiInterface;
import com.example.whatch_moovium.API_Interface.Interfaces;
import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.DatabaseHandler;
import com.example.whatch_moovium.Model.Model;
import com.example.whatch_moovium.Model.Movie;
import com.example.whatch_moovium.Model.StorageClass;
import com.example.whatch_moovium.View.MovieSuggestion;

import java.util.Collections;
import java.util.List;

public class MoodSuggPresenter implements Contract.IMoodSuggPresenter, Interfaces.apiDiscoverCallback{

    private Contract.ILandingViewMoodSugg landingPageView;
    private Contract.IImageLoader imageLoader;
    private ApiInterface myAPI_Interface;
    private int index;
    private List<ImageButton> buttons;
    private DatabaseHandler db;

    public MoodSuggPresenter(Contract.ILandingViewMoodSugg landingPageView, List<ImageButton> buttons) {
        this.landingPageView = landingPageView;
        myAPI_Interface = new ApiInterface(landingPageView.getContext());
        imageLoader = new ImageLoader(landingPageView.getContext());
        this.buttons = buttons;
        index = 0;
        this.db = new DatabaseHandler(landingPageView.getContext());
    }

    @Override
    public void onPageLoaded() {

        //Bilder laden
        for(int i = 0; i < buttons.size(); i++) {
            imageLoader.setImageView(buttons.get(i));
            imageLoader.loadImages(StorageClass.getInstance().getMyModel().getArrayList().get(index).getPoster());
            index++;
        }

    }


    @Override
    public void onMovieClick(String tablename, int i) {
        //TODO: Gewählten Film in DB speichern zur gewählten Mood
        if(db.checkIfThree(tablename)==false){
            db.addTableIMG(tablename, StorageClass.getInstance().getMyModel().getArrayList().get(index-i).getId());
        }
        getSimilarMovieListFromApi();
        toMovieSuggestion();
    }

    @Override
    public void getSimilarMovieListFromApi() {
        //TODO: API_Request:getSimilar für Movies in gewählter Mood aus DB
        myAPI_Interface.getDiscover("popularity.desc", true, StorageClass.getInstance().getProviderList(), this);

    }

    @Override
    public void receiveDiscover(List<Movie> filteredMovieList) {
        Collections.shuffle(filteredMovieList);
        StorageClass.getInstance().setMyModel(new Model(filteredMovieList));
    }


    @Override
    public void toMovieSuggestion() {
        Intent i = new Intent(landingPageView.getContext(), MovieSuggestion.class);
        landingPageView.getContext().startActivity(i);
    }

    @Override
    public void onNextButtonClick() {
        for(int i = 0; i < buttons.size(); i++) {
            imageLoader.setImageView(buttons.get(i));
            imageLoader.loadImages(StorageClass.getInstance().getMyModel().getArrayList().get(index).getPoster());
            index++;
            if(index >= 20) {
                index = 0;
            }
        }
    }
}
