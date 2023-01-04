package com.example.whatch_moovium;

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

import com.example.whatch_moovium.Model.StorageClass;
import com.example.whatch_moovium.Presenter.BottomNavPresenter;
import com.example.whatch_moovium.View.WatchlistPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class ProviderSettings extends AppCompatActivity implements Contract.IProviderRecyclerView, Contract.IBottomNavContext {

    private Contract.IBottomNavPresenter bottomNavPresenter;
    ArrayList<ProviderModel> possibleProviders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_settings);

        RecyclerView recyclerView = findViewById(R.id.providerRecycler);

        setupProvider();

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


    private void setupProvider(){
        String[] providerNames = getResources().getStringArray(R.array.possible_providers);
        List<String> providerList = new ArrayList<>();

        StorageClass.getInstance().resetSettingForGenreList();
        
        for (int i = 0; i < providerNames.length; i++){
            Switch newSwitch = new Switch(this);

            SharedPreferences getSwitchPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            boolean newBool = getSwitchPrefs.getBoolean("value"+i, true);

            newSwitch.setChecked(newBool);
            possibleProviders.add(new ProviderModel(providerNames[i], newSwitch, newBool));
            if (newBool){
                providerList.add(providerNames[i]);
            }
        }

        StorageClass.getInstance().setProviderList(providerList);

        //Test
        for (int e = 0; e < providerList.size(); e++){
            Log.i("providerLog", "" + providerList.get(e).toString());
        }

    }


    @Override
    public void onSwitchFlipped(int position, boolean switchState) {

        String providerName = possibleProviders.get(position).getProviderName();
        ProviderModel providerStatus = possibleProviders.get(position);

        SharedPreferences switchPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor switchEditor = switchPref.edit();
        if (switchState){
            Log.i("providerLog", possibleProviders.get(position).providerName + " zur Liste hinzugefügt");

            String currentPosition = providerStatus.getProviderName();
            StorageClass.getInstance().addProviderList(currentPosition);

            providerStatus.setProviderStatus(true);
            switchEditor.putBoolean("value"+position, true);
            switchEditor.apply();
        } else {
            Log.i("providerLog", possibleProviders.get(position).providerName + " von Liste entfernt");
            String currentPosition = providerStatus.getProviderName();
            StorageClass.getInstance().removeProviderList(currentPosition);
            providerStatus.setProviderStatus(false);
            switchEditor.putBoolean("value"+position, false);
            switchEditor.apply();
        }
    }

    @Override
    public Context getContextForNav() { return ProviderSettings.this; }
}