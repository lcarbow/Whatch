package com.example.whatch_moovium;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.whatch_moovium.Model.Model;
import com.example.whatch_moovium.Model.Movie;
import com.example.whatch_moovium.View.LandingPage_Genres;

import java.util.List;

public interface Contract {

    //MUELL!
    interface IMovieView {
        //MovieView zu MovieSuggestion setzen
        Context getContext();
        String getTextTitle();
        void setTitle(String string);
        void setDescription(String string);
        void setGenre(String string);
        void setRating(String string);
        void setStreaming(String string);
        void setPosterImage(Bitmap img);
        void setButtonAddVisibility(int i);
        void setButtonDeleteVisibility(int i);
        void setSeenButtonColor();
        void unsetSeenButtonColor();

    }

    interface ILandingViewGenre {
        Context getContext();
        void setAdapter();

    }

    interface ILandingViewSurprise {
        Context getContext();
    }

    interface ILandingViewMood {
        Context getContext();
    }

    interface ILandingViewMoodSugg {
        Context getContext();
        void setImageButton1(Bitmap img);
        void setImageButton2(Bitmap img);
        void setImageButton3(Bitmap img);
        void setImageButton4(Bitmap img);
    }

    interface IModelView {
        // nested interface to be
        interface OnFinishedListener {
            // function to be called
            // once the Handler of Model class
            // completes its execution
            void onFinished(Movie movie);
        }
        void getThisMovie(IModelView.OnFinishedListener onFinishedListener);
        void getNextMovie(IModelView.OnFinishedListener onFinishedListener);
        void getBeforeMovie(IModelView.OnFinishedListener onFinishedListener);

    }


    interface ISurprisePresenter {
        void getMovieListFromApi();
        void onButtonClick();
    }

    interface IGenrePresenter {
        void getMovieListFromApi();
        void setMovieVertical(int position);
        void onClickImage(View view, int adapterPosition);
        void setImageViewForLoader(ImageView imageView);
        void LoadImagesFromImageLoader(String imgPath);
    }



    interface IMoodPresenter {
        void getRandomMovieListFromApi();
        void onPageLoaded();
        void getSimilarMovieListFromApi(String tablename);
        void onEmojiClick(String button);
        void toMovieSuggestion();
        void toMoodSuggestion(String tablename);

    }

    interface IMoodSuggPresenter {
        void onPageLoaded();
        void getSimilarMovieListFromApi(String tablename);
        void onMovieClick(String tablename, int i);
        void toMovieSuggestion();
        void onNextButtonClick();
    }

    interface IMovieSuggestionPresenter {
        // method to be called when
        // the button is clicked
        void onPageLoaded();
        void onButtonAddClick();
        void onButtonDeleteClick();
        void onButtonShareClick();
        void onButtonSeenClick();
        void onButtonNextClick();
        void onButtonBeforeClick();
        void exist();
        // method to destroy
        // lifecycle of MainActivity
        //void onDestroy();
    }

    interface IImageLoader {
        void loadImages(String imgPath);
        void setImageView(ImageView imageView);
    }


    interface WatchlistPresenter{
        void getMovieListFromApi();
        void onButtonClick();
        void setImageViewForLoader(ImageView imageView);
        void LoadImagesFromImageLoader(String imgPath);
        List <Movie> getMovieList();
        void setMovie(int position);
        void onClickImage(View view, int adapterPosition);
    }

    interface LandingViewWatchlist {
        Context getContext();
        void setAdapter();
    }


    interface IProviderRecyclerView {
        void onSwitchFlipped(int position, boolean switchState);
    }

    interface IBottomNavPresenter {
        void onItemClick(MenuItem item);
    }

    interface IBottomNavContext {
        Context getContextForNav();
    }
}
