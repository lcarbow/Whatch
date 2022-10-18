package com.example.whatch_moovium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class LandingPage_Mood extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page_mood);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.mood);

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