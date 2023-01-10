package com.example.whatch_moovium.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.Model.Movie;
import com.example.whatch_moovium.R;
import com.example.whatch_moovium.Presenter.BottomNavPresenter;
import com.example.whatch_moovium.Presenter.WatchlistPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class WatchlistPage extends AppCompatActivity implements Contract.LandingViewWatchlist, Contract.IBottomNavContext{


    private Contract.IBottomNavPresenter bottomNavPresenter;
    BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private WatchlistAdapter watchlistAdapter;
    private GridLayoutManager gridLayoutManager;
    private Contract.WatchlistPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist_page);

        recyclerView = findViewById(R.id.watchlist_Recycler);

        presenter = new WatchlistPresenter(this);
        presenter.getMovieListFromApi();

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

        providersButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ProviderSettings.class)));
        //watchlistButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),WatchlistPage.class)));

    }

    @Override
    public Context getContext() {
        return WatchlistPage.this;
    }

    @Override
    public void setAdapter() {
        List<Movie> movieList = presenter.getMovieList();
        gridLayoutManager = new GridLayoutManager(WatchlistPage.this, 4);
        watchlistAdapter = new WatchlistAdapter(getApplicationContext(), presenter, movieList);
        recyclerView.setAdapter(watchlistAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);

    }

    @Override
    public Context getContextForNav() { return WatchlistPage.this; }
}