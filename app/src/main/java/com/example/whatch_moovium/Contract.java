package com.example.whatch_moovium;

import android.content.Context;
import android.graphics.Bitmap;

public interface Contract {

    interface MovieView {
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

        // method to set random
        // text on the TextView

    }

    interface Model {

        // nested interface to be
        interface OnFinishedListener {
            // function to be called
            // once the Handler of Model class
            // completes its execution
            void onFinished(Movie movie);
        }
        void getNextMovie(Contract.Model.OnFinishedListener onFinishedListener);

    }

    interface Presenter {

        // method to be called when
        // the button is clicked
        void onButtonClick();
        void onButtonAddClick();
        void onButtonShareClick();
        void onButtonSeenClick();
        void onButtonNextClick();
        void getMovieListFromApi();

        // method to destroy
        // lifecycle of MainActivity
        //void onDestroy();
    }

}
