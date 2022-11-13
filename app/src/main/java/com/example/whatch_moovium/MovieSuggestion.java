package com.example.whatch_moovium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class MovieSuggestion extends AppCompatActivity implements Contract.MovieView {

    BottomNavigationView bottomNavigationView;


    //Views initialisieren

    TextView titleView;
    TextView descriptionView;
    TextView genreView;
    TextView ratingView;
    TextView streamingView;
    ImageView movieImage;

    Button buttonShare;
    Button buttonAdd;
    Button buttonSeen;
    Button buttonNext;
    Button buttonBefore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_suggestion);

        //Views zuweisen
        titleView = findViewById(R.id.titleView);
        descriptionView = findViewById(R.id.movieDesc);
        genreView = findViewById(R.id.movieGenre);
        ratingView = findViewById(R.id.movieRating);
        streamingView = findViewById(R.id.movieStreaming);
        movieImage = findViewById(R.id.movieImg);

        buttonShare = findViewById(R.id.button_share);
        buttonAdd = findViewById(R.id.button_add);
        buttonSeen = findViewById(R.id.button_seen);
        buttonNext = findViewById(R.id.button_next);
        buttonBefore = findViewById(R.id.button_back);

        //Presenter
        Contract.Presenter presenter;
        presenter = new Presenter(this);
        presenter.getMovieListFromApi();


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.onButtonAddClick();
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
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.surprise);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.surprise:
                        startActivity(new Intent(getApplicationContext(),LandingPage_Surprise.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mood:
                        startActivity(new Intent(getApplicationContext(),LandingPage_Mood.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.genres:
                        startActivity(new Intent(getApplicationContext(),LandingPage_Genres.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
    /* Wichtig für Bildaz from Moviez
    public void displayMovie(List<Movie> movieList) {

        String uri = "@drawable/" + "hangover";  // where myresource (without the extension) is the file

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());

        Drawable res = getResources().getDrawable(imageResource);

        movieImage.setImageDrawable(res);

    }

     */

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
}