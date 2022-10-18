package com.example.whatch_moovium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LandingPage_Surprise extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page_surprise);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LandingPage_Surprise.this,MovieSuggestion.class);
                //shared Prefs @Lasse
                startActivity(i);


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