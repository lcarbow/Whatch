package com.example.whatch_moovium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieSuggestion extends AppCompatActivity implements API_Interface.apiInterfaceCallback {

    BottomNavigationView bottomNavigationView;

    DatabaseHandler databaseHandler;

    TextView titleView;
    TextView descriptionView;
    TextView genreView;
    TextView ratingView;
    TextView streamingView;
    ImageView movieImage;

    Button buttonShare;
    Button buttonAdd;
    Button buttonSeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_suggestion);

        databaseHandler = new DatabaseHandler(this);

        titleView = findViewById(R.id.titleView);
        descriptionView = findViewById(R.id.movieDesc);
        genreView = findViewById(R.id.movieGenre);
        ratingView = findViewById(R.id.movieRating);
        streamingView = findViewById(R.id.movieStreaming);
        movieImage = findViewById(R.id.movieImg);

        buttonShare = findViewById(R.id.button_share);
        buttonAdd = findViewById(R.id.button_add);
        buttonSeen = findViewById(R.id.button_seen);

        API_Interface myAPI_Interface = new API_Interface(this);
        myAPI_Interface.setCallback(this);

        myAPI_Interface.getRandom();





    }

    @Override
    public void displayMovie(List<Movie> movieList) {
/*
        String uri = "@drawable/" + "hangover";  // where myresource (without the extension) is the file

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());

        Drawable res = getResources().getDrawable(imageResource);

        movieImage.setImageDrawable(res);*/

        titleView.setText(movieList.get(0).getTitle());
        descriptionView.setText(movieList.get(0).getDescription());
        genreView.setText(movieList.get(0).getGenre());
        ratingView.setText(String.format("%.1f", (movieList.get(0).getRating()*10)) + "% Benutzerbewertung");
        streamingView.setText("Als Stream verfügbar auf " + movieList.get(0).getStreaming());

        Log.i("Hilfe", "Hilf mir bitte");

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Lasse Database
                databaseHandler.addWatchlistMovie(movieList.get(0).getId());
                Toast.makeText(MovieSuggestion.this,
                        "Zur Watchlist hinzugefügt!", Toast.LENGTH_SHORT).show();
            }
        });

        buttonSeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Lasse Database
                databaseHandler.addSeenlistMovie(movieList.get(0).getId());
                Toast.makeText(MovieSuggestion.this,
                        "Zur Gesehenlist hinzugefügt!", Toast.LENGTH_SHORT).show();
            }
        });

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Schau, welchen coolen Film ich gefunden habe: " + movieList.get(0).getTitle());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);

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
}