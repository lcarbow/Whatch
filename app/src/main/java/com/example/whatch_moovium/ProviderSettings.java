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

public class ProviderSettings extends AppCompatActivity implements ProviderRecyclerViewInterface {

    Provider providerElementTest = new Provider(0);

    BottomNavigationView bottomNavigationView;
    ArrayList<ProviderModel> possibleProviders = new ArrayList<>();

    //Diese Liste hier später mit der Providerliste aus der DB ersetzen
    ArrayList<String> listOfProvidersTest = new ArrayList<>();

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


        for (int i = 0; i < providerNames.length; i++){
            Switch newSwitch = new Switch(this);

            int currentProvider = Integer.parseInt(providerIDs[i]);
            //StorageClass.getInstance().addProviderIdList(new Provider(currentProvider));

            SharedPreferences getSwitchPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            boolean newBool = getSwitchPrefs.getBoolean("value"+i, true);

            int currentId = Integer.parseInt(providerIDs[i]);
            newSwitch.setChecked(newBool);
            possibleProviders.add(new ProviderModel(providerNames[i], providerIDs[i], newSwitch, newBool));


            providerElementTest.setId(possibleProviders.get(i).getProviderID());
            StorageClass.getInstance().addProviderIdList(providerElementTest);

            //NOTE: diesen Teil entfernen wenn Database-Einbindung vorhanden
            listOfProvidersTest.add(providerNames[i]);
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
            //StorageClass.getInstance().setProviderList(Arrays.asList(8,337));
            /*StorageClass.getInstance().addProviderIdList(8);*/
            providerElementTest.setId(possibleProviders.get(position).getProviderID());
            StorageClass.getInstance().addProviderIdList(providerElementTest);

            listOfProvidersTest.add(providerName);
            providerStatus.setProviderStatus(true);
            switchEditor.putBoolean("value"+position, true);
            switchEditor.apply();
        } else {
            Log.i("providerLog", possibleProviders.get(position).providerID + " von Liste entfernt");
            providerElementTest.setId(possibleProviders.get(position).getProviderID());
            StorageClass.getInstance().removeProviderIdList(providerElementTest);
            listOfProvidersTest.remove(providerName);
            providerStatus.setProviderStatus(false);
            switchEditor.putBoolean("value"+position, false);
            switchEditor.apply();
        }
    }
}