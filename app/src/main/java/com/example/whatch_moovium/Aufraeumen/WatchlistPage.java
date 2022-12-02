package com.example.whatch_moovium.Aufraeumen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.example.whatch_moovium.R;
import com.example.whatch_moovium.View.LandingPage_Genres;
import com.example.whatch_moovium.View.LandingPage_Mood;
import com.example.whatch_moovium.View.LandingPage_Surprise;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class WatchlistPage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist_page);

        //Bottom Nav
        bottomNavigationView = findViewById(R.id.bottom_navigator);

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
                        startActivity(new Intent(getApplicationContext(), LandingPage_Mood.class));
                        overridePendingTransition(0,0);
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
        //ImageButton watchlistButton = findViewById(R.id.watchlist_button);

        providersButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),ProviderSettings.class)));
        //watchlistButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),WatchlistPage.class)));
    }
}