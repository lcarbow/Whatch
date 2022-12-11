package com.example.whatch_moovium.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.Presenter.MoodPresenter;
import com.example.whatch_moovium.ProviderSettings;
import com.example.whatch_moovium.R;
import com.example.whatch_moovium.WatchlistPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class LandingPage_Mood extends AppCompatActivity implements Contract.ILandingViewMood{

    BottomNavigationView bottomNavigationView;
    private Contract.IMoodPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page_mood);

        presenter = new MoodPresenter(this);
        presenter.onPageLoaded();

        ImageButton button1 = findViewById(R.id.imageButton1);
        ImageButton button2 = findViewById(R.id.imageButton2);
        ImageButton button3 = findViewById(R.id.imageButton3);
        ImageButton button4 = findViewById(R.id.imageButton4);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.mood);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onEmojiClick();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onEmojiClick();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onEmojiClick();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onEmojiClick();
            }
        });

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
                }
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
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }

    @Override
    public Context getContext() {
        return LandingPage_Mood.this;
    }

}