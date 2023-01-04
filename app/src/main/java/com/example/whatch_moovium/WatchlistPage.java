package com.example.whatch_moovium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.example.whatch_moovium.Presenter.BottomNavPresenter;
import com.example.whatch_moovium.View.LandingPage_ProviderSettings;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class WatchlistPage extends AppCompatActivity implements Contract.IBottomNavContext {

    private Contract.IBottomNavPresenter bottomNavPresenter;

    private final Integer image_tests[] = {
            R.drawable.hangover,
            R.drawable.godzilla,
            R.drawable.hangover,
            R.drawable.godzilla,
            R.drawable.hangover,
            R.drawable.godzilla,
            R.drawable.hangover,
            R.drawable.godzilla,
            R.drawable.hangover,
            R.drawable.godzilla,
            R.drawable.hangover,
            R.drawable.godzilla,
            R.drawable.hangover,
            R.drawable.godzilla,
            R.drawable.hangover,
            R.drawable.godzilla,
            R.drawable.hangover,
            R.drawable.godzilla,
            R.drawable.hangover,
            R.drawable.godzilla,
            R.drawable.hangover,
            R.drawable.godzilla
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist_page);


        RecyclerView recyclerView = findViewById(R.id.watchlist_Recycler);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 4);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<WatchlistModel> watchlistModels = setupPosters();
        WatchlistAdapter adapter = new WatchlistAdapter(getApplicationContext(), watchlistModels);
        recyclerView.setAdapter(adapter);



        //Bottom Nav
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottom_navigator);

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
        //ImageButton watchlistButton = findViewById(R.id.watchlist_button);

        providersButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), LandingPage_ProviderSettings.class)));
        //watchlistButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),WatchlistPage.class)));

    }

    private ArrayList<WatchlistModel> setupPosters() {
        ArrayList<WatchlistModel> posters = new ArrayList<>();
        for (int i = 0;  i < image_tests.length; i++){
            WatchlistModel watchlistModel = new WatchlistModel();
            watchlistModel.setPoster(image_tests[i]);
            posters.add(watchlistModel);
        }
        return posters;
    }

    @Override
    public Context getContextForNav() { return WatchlistPage.this; }
}