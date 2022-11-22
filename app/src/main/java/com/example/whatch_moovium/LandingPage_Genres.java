package com.example.whatch_moovium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class LandingPage_Genres extends AppCompatActivity implements Contract.LandingView {

    BottomNavigationView bottomNavigationView;
    private ArrayList<LandingPage_Genres_ModelParent> titleList;
    private RecyclerView ParentRecyclerViewItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page_genres);

        ParentRecyclerViewItem = findViewById(R.id.parent_recyclerview);

        //In Presenter auslagern
        LinearLayoutManager layoutManager = new LinearLayoutManager(LandingPage_Genres.this);
        LandingPage_Genre_ParentItemAdapter parentItemAdapter = new LandingPage_Genre_ParentItemAdapter(ParentItemList());
        ParentRecyclerViewItem.setAdapter(parentItemAdapter);
        ParentRecyclerViewItem.setLayoutManager(layoutManager);

        Contract.MovieListPresenter presenter;
        presenter = new MovieListPresenter(this);
        presenter.getMovieListFromApi();


        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.genres);


        //setTitleInfos();
        //setAdapter();

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
                        startActivity(new Intent(getApplicationContext(),LandingPage_Mood.class));
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

        providersButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),ProviderSettings.class)));
        watchlistButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),WatchlistPage.class)));

    }
    //In Presenter auslagern
    private List<LandingPage_Genres_ModelParent> ParentItemList()
    {
        List<LandingPage_Genres_ModelParent> itemList = new ArrayList<>();

        String[] genreList = getResources().getStringArray(R.array.genrelist);

        for (int i = 0; i < genreList.length; i++){

            itemList.add(new LandingPage_Genres_ModelParent(genreList[i], ChildItemList()));

        }

        return itemList;
    }
    //In Presenter auslagern
    private List<Movie> ChildItemList()
    {
        List<Movie> ChildItemList = new ArrayList<>();


        ChildItemList.add(new Movie());
        ChildItemList.add(new Movie());
        ChildItemList.add(new Movie());
        ChildItemList.add(new Movie());
        ChildItemList.add(new Movie());
        ChildItemList.add(new Movie());
        ChildItemList.add(new Movie());
        ChildItemList.add(new Movie());
        ChildItemList.add(new Movie());




        return ChildItemList;
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
        return LandingPage_Genres.this;    }
}