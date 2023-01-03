package com.example.whatch_moovium;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.whatch_moovium.API_Interface.*;
import com.example.whatch_moovium.Model.Model;
import com.example.whatch_moovium.Model.Movie;
import com.example.whatch_moovium.Model.StorageClass;
import com.example.whatch_moovium.Presenter.ImageLoader;
import com.example.whatch_moovium.View.MovieSuggestion;

import java.util.ArrayList;
import java.util.List;

public class WatchlistPresenter implements Contract.WatchlistPresenter, Contract.IModelView.OnFinishedListener,Interfaces.apiWatchlistCallback, Interfaces.apiBackdropCallback, Interfaces.apiPosterCallback{
    private Contract.LandingViewWatchlist landingPageWatchlist;
    private Contract.IModelView model;
    private Contract.IImageLoader imageLoader;

    ApiInterface myApiInterface;
    DatabaseHandler dbHandler;
    private List<Movie> movieList;
    private RecyclerView.Adapter adapter;
    int index;

    public WatchlistPresenter(Contract.LandingViewWatchlist landingPageWatchlist){
        this.landingPageWatchlist = landingPageWatchlist;
        this.myApiInterface = new ApiInterface(landingPageWatchlist.getContext());
        this.imageLoader = new ImageLoader(landingPageWatchlist.getContext());
        this.dbHandler = new DatabaseHandler(landingPageWatchlist.getContext());

    }

    @Override
    public List <Movie> getMovieList(){return movieList;}

    @Override
    public void onClickImage(View view, int adapterPosition) {
        Intent i = new Intent(view.getContext(), MovieSuggestion.class);
        view.getContext().startActivity(i);

    }


    @Override
    public void getMovieListFromApi() {
        List<Integer> watchList = new ArrayList<>();
        watchList = dbHandler.getWatchlist();
        myApiInterface.getWatchlist(watchList, this);
    }

    @Override
    public void onButtonClick(){
        if (landingPageWatchlist != null) {
            Intent i = new Intent(landingPageWatchlist.getContext(), MovieSuggestion.class);
            landingPageWatchlist.getContext().startActivity(i);
            Log.i("userdebug","Umleitung zur MovieSugg");

        }
    }

    @Override
    public void receiveWatchlist(List<Movie> watchList){
        movieList = watchList;
        for (Movie movie : movieList) {
            myApiInterface.getPoster(movie.getPoster(), this);
        }
        landingPageWatchlist.setAdapter();
    }

    @Override
    public void receivePoster(Bitmap img){

        Movie movie = movieList.get(0);
        movie.setPosterBitmap(img);
        movieList.remove(0);
        /*if (movieList.isEmpty()) {
            landingPageWatchlist.setAdapter();
        }*/
    }

    @Override
    public void receiveBackdrop(Bitmap img){

    }

    @Override
    public void onFinished(Movie movie){

    }

    @Override
    public void setImageViewForLoader(ImageView imageView){imageLoader.setImageView(imageView);}

    @Override
    public void LoadImagesFromImageLoader(String imgPath) {imageLoader.loadImages(imgPath);}


}
