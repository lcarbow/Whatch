package com.example.whatch_moovium;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.whatch_moovium.Model.Model;
import com.example.whatch_moovium.Model.Movie;

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


    }

    interface ILandingViewGenre {
        Context getContext();
        void setAdapter(List<Model> itemList);
        void dataChange();

    }

    interface ILandingViewSurprise {
        Context getContext();
    }

    interface ILandingViewMood {
        Context getContext();
    }

    //MUELL!
    interface LandingView {

        Context getContext();
        /*
        // method to display progress bar
        // when next random course details
        // is being fetched
        void showProgress();

        // method to hide progress bar
        // when next random course details
        // is being fetched
        void hideProgress();


         titleView.setText(movieList.get(0).getTitle());
        descriptionView.setText(movieList.get(0).getDescription());
        genreView.setText(movieList.get(0).getGenre());
        ratingView.setText(String.format("%.1f", (movieList.get(0).getRating()*10)) + "% Benutzerbewertung");
        streamingView.setText
        */

        // method to set random//
        // text on the TextView

    }

    interface IModelView {
        // nested interface to be
        interface OnFinishedListener {
            // function to be called
            // once the Handler of Model class
            // completes its execution
            void onFinished(Movie movie);
        }
        void getNextMovie(IModelView.OnFinishedListener onFinishedListener);
        void getBeforeMovie(IModelView.OnFinishedListener onFinishedListener);
        void nextIndex();
        int showIndex();
    }


    interface ISurprisePresenter {
        void getMovieListFromApi();
        void onButtonClick();
    }

    interface IGenrePresenter {
        void getMovieListFromApi();
        void loadImages(int vertical, int horizontal);

    }



    interface IMoodPresenter {
        void getMovieListFromApi();
        void onButtonClick();
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
        // method to destroy
        // lifecycle of MainActivity
        //void onDestroy();
    }


}
