package com.example.whatch_moovium.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Switch;

import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.Model.ProviderModel;
import com.example.whatch_moovium.Model.StorageClass;
import com.example.whatch_moovium.Presenter.BottomNavPresenter;
import com.example.whatch_moovium.Presenter.ProviderAdapter;
import com.example.whatch_moovium.Presenter.ProviderPresenter;
import com.example.whatch_moovium.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class LandingPage_ProviderSettings extends AppCompatActivity implements Contract.IProviderRecyclerView, Contract.IBottomNavContext, Contract.LandingViewProvider {

    private Contract.IBottomNavPresenter bottomNavPresenter;
    ArrayList<ProviderModel> possibleProviders = new ArrayList<>();
    ProviderPresenter pp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_settings);

        RecyclerView recyclerView = findViewById(R.id.providerRecycler);
        pp = new ProviderPresenter(this);

        pp.onPageLoaded();

        ProviderAdapter adapter = new ProviderAdapter(this, possibleProviders, this);

        //Adapter an RecyclerView ranhängen
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
        //ImageButton providersButton = findViewById(R.id.providers_button);
        ImageButton watchlistButton = findViewById(R.id.watchlist_button);

        //providersButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),ProviderSettings.class)));
        watchlistButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), WatchlistPage.class)));
    }


    @Override
    public void onSwitchFlipped(int position, boolean switchState) {
        pp.switchFlip(position, switchState);
    }

    @Override
    public Context getContextForNav() { return LandingPage_ProviderSettings.this; }

    @Override
    public Context getContext(){return LandingPage_ProviderSettings.this;}

    @Override
    public Context getBaseContext(){return getBaseContext();}
}