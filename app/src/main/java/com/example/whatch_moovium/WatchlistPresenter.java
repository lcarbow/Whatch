package com.example.whatch_moovium;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.whatch_moovium.API_Interface.*;
import com.example.whatch_moovium.Model.Model;
import com.example.whatch_moovium.Model.Movie;
import com.example.whatch_moovium.Model.StorageClass;
import com.example.whatch_moovium.Presenter.ImageLoader;
import com.example.whatch_moovium.View.MovieSuggestion;

import java.util.ArrayList;
import java.util.List;

public class WatchlistPresenter implements Contract.WatchlistPresenter, Interfaces.apiWatchlistCallback, Interfaces.apiPosterCallback{
    private Contract.LandingViewWatchlist landingPageWatchlist;
    private Contract.IModelView model;
    private Contract.IImageLoader imageLoader;

    ApiInterface myApiInterface;
    DatabaseHandler dbHandler;
    private List<Movie> movieList;
    int index;


    public WatchlistPresenter(Contract.LandingViewWatchlist landingPageWatchlist){
        this.landingPageWatchlist = landingPageWatchlist;
        this.myApiInterface = new ApiInterface(landingPageWatchlist.getContext());
        this.imageLoader = new ImageLoader(landingPageWatchlist.getContext());
        this.dbHandler = new DatabaseHandler(landingPageWatchlist.getContext());
        this.movieList = new ArrayList<>();

    }

    @Override
    public List <Movie> getMovieList(){return movieList;}

    @Override
    public void onClickImage(View view, int adapterPosition) {
        Intent i = new Intent(view.getContext(), MovieSuggestion.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        view.getContext().startActivity(i);
        StorageClass.getInstance().getMyModel().setIndex(adapterPosition);
    }



    @Override
    public void getMovieListFromApi() {
        StorageClass.getInstance().resetSettingForWatchList();
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
    public void receiveWatchlist(List<Movie> watchList) {
        this.movieList = watchList;
        this.index = 0;  // Initialize the index variable
        StorageClass.getInstance().addWatchModelList(new Model (watchList));
        for (Movie movie : movieList) {
            myApiInterface.getPoster(movie.getPoster(), this);
            Log.i("receiveMovies", movie.getTitle());
        }
    }

    @Override
    public void receivePoster(Bitmap poster) {
        Movie movie = movieList.get(index);  // Get the movie for the current index
        movie.setPosterBitmap(poster);  // Set the poster for the movie
        index++;  // Increment the index
        StorageClass.getInstance().getWatchMovieList().add(movie);
        Log.i("receivePoster", movie.getTitle());
        if (index == movieList.size()) {  // If all posters have been received
            landingPageWatchlist.setAdapter();  // Set the adapter for the RecyclerView
        }
    }


    @Override
    public void setImageViewForLoader(ImageView imageView){imageLoader.setImageView(imageView);}

    @Override
    public void LoadImagesFromImageLoader(String imgPath) {imageLoader.loadImages(imgPath);}

    @Override
    public void setMovie(int position){
        StorageClass.getInstance().setMyModel(StorageClass.getInstance().getWatchlistModelList().get(0));
        StorageClass.getInstance().getMyModel().setIndex(position);
    }

}
