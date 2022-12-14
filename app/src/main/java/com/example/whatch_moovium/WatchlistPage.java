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


import com.example.whatch_moovium.Model.Movie;
import com.example.whatch_moovium.View.LandingPage_Genres;
import com.example.whatch_moovium.View.LandingPage_Mood;
import com.example.whatch_moovium.View.LandingPage_Surprise;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class WatchlistPage extends AppCompatActivity implements Contract.LandingViewWatchlist {

    BottomNavigationView bottomNavigationView;
    private Contract.WatchlistPresenter presenter;

    /*
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
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist_page);

        //Presenter
        presenter = new WatchlistPresenter(this);
        presenter.getMovieListFromApi();

        //Recycler
        RecyclerView recyclerView = findViewById(R.id.watchlist_Recycler);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 4);
        recyclerView.setLayoutManager(layoutManager);

        List<Movie> watchlistModels = setupWatchlist();
        WatchlistAdapter adapter = new WatchlistAdapter(presenter, getApplicationContext(), watchlistModels);
        recyclerView.setAdapter(adapter);

/* //Watchlist-Display Test
        RecyclerView recyclerView = findViewById(R.id.watchlist_Recycler);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 4);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<WatchlistModel> watchlistModels = TestSetupPosters();
        WatchlistAdapter adapter = new WatchlistAdapter(getApplicationContext(), watchlistModels);
        recyclerView.setAdapter(adapter);

 */



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

    /*//Watchlist TestSetup
    private ArrayList<WatchlistModel> testSetupPosters() {
        ArrayList<WatchlistModel> posters = new ArrayList<>();
        for (int i = 0;  i < image_tests.length; i++){
            WatchlistModel watchlistModel = new WatchlistModel();
            watchlistModel.setPoster(image_tests[i]);
            posters.add(watchlistModel);
        }
        return posters;
    }*/

    private List<Movie> setupWatchlist(){

        List<Movie> posters = new ArrayList<>();
        //TODO get WatchlistIDs - turn them into Movie Objects and put them in "posters"
        return posters;
    }

    @Override
    public Context getContext() {
        return WatchlistPage.this;
    }


    @Override
    public void setAdapter(List<Movie> itemList) {

    }
}