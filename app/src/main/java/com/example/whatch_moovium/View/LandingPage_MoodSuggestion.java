package com.example.whatch_moovium.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.Model.Model;
import com.example.whatch_moovium.Presenter.MoodPresenter;
import com.example.whatch_moovium.Presenter.MoodSuggPresenter;
import com.example.whatch_moovium.ProviderSettings;
import com.example.whatch_moovium.R;
import com.example.whatch_moovium.WatchlistPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LandingPage_MoodSuggestion extends AppCompatActivity implements Contract.ILandingViewMoodSugg {

    private Contract.IMoodSuggPresenter presenter;

    ImageButton button1;
    ImageButton button2;
    ImageButton button3;
    ImageButton button4;

    Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page_mood_suggestion);


        button1 = findViewById(R.id.imageButton1);
        button2 = findViewById(R.id.imageButton2);
        button3 = findViewById(R.id.imageButton3);
        button4 = findViewById(R.id.imageButton4);
        buttonNext = findViewById(R.id.buttonNext);

        presenter = new MoodSuggPresenter(this, Arrays.asList(button1, button2, button3, button4));
        presenter.onPageLoaded();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onMovieClick();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onMovieClick();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onMovieClick();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onMovieClick();
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onNextButtonClick();
            }
        });


//Bottom Nav
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.mood);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.surprise:
                        startActivity(new Intent(getApplicationContext(), LandingPage_Surprise.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mood:
                        return true;
                    case R.id.genres:
                        startActivity(new Intent(getApplicationContext(), LandingPage_Genres.class));
                        overridePendingTransition(0,0);
                        return true;
                    default:
                        return false;
                }
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
        return LandingPage_MoodSuggestion.this;
    }

    @Override
    public void setImageButton1(Bitmap img) {
        button1.setImageBitmap(img);
    }

    @Override
    public void setImageButton2(Bitmap img) {
        button2.setImageBitmap(img);

    }

    @Override
    public void setImageButton3(Bitmap img) {
        button3.setImageBitmap(img);

    }

    @Override
    public void setImageButton4(Bitmap img) {
        button4.setImageBitmap(img);

    }
}