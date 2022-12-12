package com.example.whatch_moovium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Switch;

import com.example.whatch_moovium.Model.StorageClass;
import com.example.whatch_moovium.View.LandingPage_Genres;
import com.example.whatch_moovium.View.LandingPage_Mood;
import com.example.whatch_moovium.View.LandingPage_Surprise;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProviderSettings extends AppCompatActivity implements ProviderRecyclerViewInterface {

    BottomNavigationView bottomNavigationView;
    ArrayList<ProviderModel> possibleProviders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_settings);

        RecyclerView recyclerView = findViewById(R.id.provider_Recycler);

        setupProvider();

        ProviderAdapter adapter = new ProviderAdapter(this, possibleProviders, this);

        //Adapter an RecyclerView ranhängen
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
        //ImageButton providersButton = findViewById(R.id.providers_button);
        ImageButton watchlistButton = findViewById(R.id.watchlist_button);

        //providersButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),ProviderSettings.class)));
        watchlistButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),WatchlistPage.class)));

    }


    private void setupProvider(){
        String[] providerNames = getResources().getStringArray(R.array.possible_providers);
        String[] providerIDs = getResources().getStringArray(R.array.possible_providerIDs);
        List<Integer> providerList = new ArrayList<>();

        StorageClass.getInstance().resetSettingForGenreList();
        
        for (int i = 0; i < providerNames.length; i++){
            Switch newSwitch = new Switch(this);

            SharedPreferences getSwitchPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            boolean newBool = getSwitchPrefs.getBoolean("value"+i, true);

            int currentId = Integer.parseInt(providerIDs[i]);
            newSwitch.setChecked(newBool);
            possibleProviders.add(new ProviderModel(providerNames[i], currentId, newSwitch, newBool));
            if (newBool){
                providerList.add(currentId);
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
            Log.i("providerLog", possibleProviders.get(position).providerID + " zur Liste hinzugefügt");

            int currentPositionID = providerStatus.getProviderID();
            StorageClass.getInstance().addProviderIdList(currentPositionID);

            providerStatus.setProviderStatus(true);
            switchEditor.putBoolean("value"+position, true);
            switchEditor.apply();
        } else {
            Log.i("providerLog", possibleProviders.get(position).providerID + " von Liste entfernt");
            int currentPositionID = providerStatus.getProviderID();
            StorageClass.getInstance().removeProviderIdList(currentPositionID);
            providerStatus.setProviderStatus(false);
            switchEditor.putBoolean("value"+position, false);
            switchEditor.apply();
        }
    }
}