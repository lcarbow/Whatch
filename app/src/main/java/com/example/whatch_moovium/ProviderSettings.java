package com.example.whatch_moovium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class ProviderSettings extends AppCompatActivity implements ProviderRecyclerViewInterface {

    BottomNavigationView bottomNavigationView;
    ArrayList<ProviderModel> possibleProviders = new ArrayList<>();


    //ArrayList<String> selectedProviders = new ArrayList<>();

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
                        startActivity(new Intent(getApplicationContext(),LandingPage_Surprise.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mood:
                        startActivity(new Intent(getApplicationContext(),LandingPage_Mood.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.genres:
                        startActivity(new Intent(getApplicationContext(),LandingPage_Genres.class));
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
        //Switch[] switches = new Switch[providerNames.length];
        //@Nadine: Das Ding ist.. Du hast ne leere liste erstellt :D


        for (int i = 0; i < providerNames.length; i++){
            Switch switchh = new Switch(this);
            //@Nadine: Hab jetzt hier einfach bei jedem Schleifendurchlauf ne Switch erstellt die dann direkt zur Liste zugefügt wird.

            possibleProviders.add(new ProviderModel(providerNames[i], switchh));
        }

    }


    @Override
    public void onSwitchFlipped(int position) {

        String providerName = possibleProviders.get(position).getProviderName();

        Switch currentSwitch = possibleProviders.get(position).getProviderSwitch();

        if (currentSwitch != null){
            //@Nadine. Die Switch existiert, aber iwie wird hier nur die TextView genommen.
            // Das mit onCheckedChanged wird also nicht erkannt...
            Log.i("userdebug", "Ich werde ausgeführt,es existiert eine Switch. nützt aber nix");
            currentSwitch.setChecked(true);
            currentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        //selectedProviders.add(providerName);
                        //currentSwitch.setText("hab ich");
                        Log.i("userdebug", "Ich werde nicht ausgeführt");

                    } else {
                        //selectedProviders.remove(providerName);
                        //currentSwitch.setText("Hab ich nicht");
                        Log.i("userdebug", "Ich werde nicht ausgeführt");

                    }
                }
            });
        }
    }
}