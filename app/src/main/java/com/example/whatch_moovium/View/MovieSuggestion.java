package com.example.whatch_moovium.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.Model.Movie;
import com.example.whatch_moovium.Model.StorageClass;
import com.example.whatch_moovium.Presenter.BottomNavPresenter;
import com.example.whatch_moovium.Presenter.MovieSuggestionPresenter;
import com.example.whatch_moovium.ProviderSettings;
import com.example.whatch_moovium.R;
import com.example.whatch_moovium.WatchlistPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MovieSuggestion extends AppCompatActivity implements Contract.IMovieView, Contract.IBottomNavContext {

    private Contract.IMovieSuggestionPresenter presenter;
    private Contract.IBottomNavPresenter bottomNavPresenter;

    //Views initialisieren

    TextView titleView;
    TextView descriptionView;
    TextView genreView;
    TextView ratingView;
    TextView streamingView;
    ImageView movieImage;

    Button buttonShare;
    Button buttonAdd;
    Button buttonDelete;
    Button buttonSeen;
    Button buttonNext;
    Button buttonBefore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_suggestion);

        Log.i("architectureLog", "OnCreate");

        //Views zuweisen
        titleView = findViewById(R.id.titleView);
        descriptionView = findViewById(R.id.movieDesc);
        genreView = findViewById(R.id.movieGenre);
        ratingView = findViewById(R.id.movieRating);
        streamingView = findViewById(R.id.movieStreaming);
        movieImage = findViewById(R.id.movieImg);

        buttonShare = findViewById(R.id.button_share);
        buttonAdd = findViewById(R.id.button_add);
        buttonDelete = findViewById(R.id.button_delete);
        buttonSeen = findViewById(R.id.button_seen);
        buttonNext = findViewById(R.id.button_next);
        buttonBefore = findViewById(R.id.button_back);

        //Presenter
        presenter = new MovieSuggestionPresenter(this);
        presenter.onPageLoaded();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.onButtonAddClick();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view) {
                presenter.onButtonDeleteClick();
            }
        });

        buttonSeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onButtonSeenClick();
            }
        });

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onButtonShareClick();

            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                presenter.onButtonNextClick();

            }
        });

        buttonBefore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                presenter.onButtonBeforeClick();

            }
        });

        //Bottom Nav
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.surprise);

        bottomNavPresenter = new BottomNavPresenter(this);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                bottomNavPresenter.onItemClick(item);
                overridePendingTransition(0,0);
                return false;
            }
        });


        //Top Nav
        ImageButton providersButton = findViewById(R.id.providers_button);
        ImageButton watchlistButton = findViewById(R.id.watchlist_button);

        providersButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ProviderSettings.class)));
        watchlistButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), WatchlistPage.class)));

    }

    @Override
    public Context getContext() {
        return MovieSuggestion.this;
    }


    @Override
    public void setTitle(String string) {
        titleView.setText(string);

    }

    public String getTextTitle() {

        return titleView.getText().toString();

    }

    @Override
    public void setDescription(String string) {
        descriptionView.setText(string);

    }

    @Override
    public void setGenre(String string) {
        genreView.setText(string);

    }

    @Override
    public void setRating(String string) {
        ratingView.setText(string);

    }

    @Override
    public void setStreaming(String string) {
        streamingView.setText(string);

    }

    @Override
    public void setPosterImage(Bitmap img) {
        movieImage.setImageBitmap(img);
    }

    @Override
    public Context getContextForNav() { return MovieSuggestion.this; }

    @Override
    public void setButtonAddVisibility(int visibility){
        buttonAdd.setVisibility(visibility);
    };
    @Override
    public void setButtonDeleteVisibility(int visibility){
        buttonDelete.setVisibility(visibility);
    };
    @Override
    public void setSeenButtonColor(){
        buttonSeen.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(153, 204, 0)));
    }

    @Override
    public void unsetSeenButtonColor(){
        buttonSeen.setBackgroundTintList(ColorStateList.valueOf(Color.argb(10,204, 204, 204)));
    }

}