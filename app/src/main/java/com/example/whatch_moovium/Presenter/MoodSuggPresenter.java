package com.example.whatch_moovium.Presenter;

import android.content.Intent;
import android.widget.ImageButton;

import com.example.whatch_moovium.API_Interface.ApiHandler;
import com.example.whatch_moovium.API_Interface.Interfaces;
import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.DatabaseHandler;
import com.example.whatch_moovium.Model.Model;
import com.example.whatch_moovium.Model.Movie;
import com.example.whatch_moovium.Model.StorageClass;
import com.example.whatch_moovium.View.MovieSuggestion;

import java.util.Collections;
import java.util.List;

public class MoodSuggPresenter implements Contract.IMoodSuggPresenter, Interfaces.apiSimilarCallback{

    private Contract.ILandingViewMoodSugg landingPageView;
    private Contract.IImageLoader imageLoader;
    private ApiHandler myAPI_Interface;
    private int index;
    private List<ImageButton> buttons;
    private DatabaseHandler db;

    public MoodSuggPresenter(Contract.ILandingViewMoodSugg landingPageView, List<ImageButton> buttons) {
        this.landingPageView = landingPageView;
        myAPI_Interface = new ApiHandler(landingPageView.getContext());
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
        if(!db.checkIfThree(tablename)){
            db.addTableIMG(tablename, StorageClass.getInstance().getMyModel().getArrayList().get(index-i).getId());
        }
        getSimilarMovieListFromApi(tablename);

    }

    @Override
    public void getSimilarMovieListFromApi(String tablename) {
        //TODO: API_Request:getSimilar für Movies in gewählter Mood aus DB
        myAPI_Interface.getSimilar(db.getMoodlist(tablename), true, StorageClass.getInstance().getProviderList(), 7, this);

    }

    @Override
    public void receiveSimilar(List<Movie> filteredMovieList) {
        Collections.shuffle(filteredMovieList);
        StorageClass.getInstance().setMyModel(new Model(filteredMovieList));
        toMovieSuggestion();
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
