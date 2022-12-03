package com.example.whatch_moovium.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.Model.Model;
import com.example.whatch_moovium.Presenter.GenrePresenter;
import com.example.whatch_moovium.Aufraeumen.ProviderSettings;
import com.example.whatch_moovium.R;
import com.example.whatch_moovium.Aufraeumen.WatchlistPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class LandingPage_Genres extends AppCompatActivity implements Contract.ILandingViewGenre {

    BottomNavigationView bottomNavigationView;
    private ArrayList<Model> titleList;
    private RecyclerView ParentRecyclerViewItem;
    private LandingPage_Genre_ParentItemAdapter parentItemAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page_genres);

        //RecyclerView
        ParentRecyclerViewItem = findViewById(R.id.parent_recyclerview);

        //Presenter
        Contract.IGenrePresenter presenter;
        presenter = new GenrePresenter(this);
        presenter.getMovieListFromApi();

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.genres);

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
        return LandingPage_Genres.this;
    }

    @Override
    public void setAdapter(List<Model> itemList) {
        layoutManager = new LinearLayoutManager(LandingPage_Genres.this);
        parentItemAdapter = new LandingPage_Genre_ParentItemAdapter(itemList);
        ParentRecyclerViewItem.setAdapter(parentItemAdapter);
        ParentRecyclerViewItem.setLayoutManager(layoutManager);

    }



    //Adapter setten, Dieser wird vom Presenter aufgerufen
    //Dafür wird eine Liste von ModelPartens übergeben
    // Diese besteht aus den GenreTitlen (String) und einer Liste von Movies für das jeweilige Genre

}