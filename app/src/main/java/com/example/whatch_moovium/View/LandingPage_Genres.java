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
import com.example.whatch_moovium.Model.StorageClass;
import com.example.whatch_moovium.Presenter.GenrePresenter;
import com.example.whatch_moovium.ProviderSettings;
import com.example.whatch_moovium.R;
import com.example.whatch_moovium.WatchlistPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LandingPage_Genres extends AppCompatActivity implements Contract.ILandingViewGenre {

    BottomNavigationView bottomNavigationView;
    private RecyclerView ParentRecyclerViewItem;
    private LandingPage_Genre_ParentItemAdapter parentItemAdapter;
    private LinearLayoutManager layoutManager;
    private Contract.IGenrePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page_genres);

        //Presenter
        presenter = new GenrePresenter(this);
        presenter.getMovieListFromApi();

        //RecyclerView
        ParentRecyclerViewItem = findViewById(R.id.parent_recyclerview);




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
    public void setAdapter() {
        Log.i("test", "else 2");
        layoutManager = new LinearLayoutManager(LandingPage_Genres.this);
        Log.i("test", "else 3");
        parentItemAdapter = new LandingPage_Genre_ParentItemAdapter(presenter);
        Log.i("test", "else 4");
        ParentRecyclerViewItem.setAdapter(parentItemAdapter);
        Log.i("test", "else 5");
        ParentRecyclerViewItem.setLayoutManager(layoutManager);
        Log.i("test", "else 6");


    }

}