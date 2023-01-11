package com.example.whatch_moovium.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;

import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.Model.ProviderModel;
import com.example.whatch_moovium.Model.StorageClass;
import com.example.whatch_moovium.Presenter.ProviderAdapter;
import com.example.whatch_moovium.Presenter.ProviderPresenter;
import com.example.whatch_moovium.R;
import com.example.whatch_moovium.View.LandingPage_Surprise;

import java.util.ArrayList;
import java.util.List;

public class Dispatcher extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Class<?> activityClass;
        List<String> providers;
        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);

        /////TEST/////
        String[] providerNames = getResources().getStringArray(R.array.possible_providers);
        List<String> providerList = new ArrayList<>();

        StorageClass.getInstance().resetSettingForGenreList();

        for (int i = 0; i < providerNames.length; i++){
            Switch newSwitch = new Switch(getApplicationContext());

            boolean newBool = prefs.getBoolean("value"+i, true);

            newSwitch.setChecked(newBool);

            if (newBool){
                providerList.add(providerNames[i]);
            }
        }

        StorageClass.getInstance().setProviderList(providerList);

        //Test
        for (int e = 0; e < providerList.size(); e++){
            Log.i("providerLog", "" + providerList.get(e).toString());
        }

        try {

            activityClass = Class.forName(
                    prefs.getString("lastActivity", LandingPage_Surprise.class.getName()));
        } catch(ClassNotFoundException ex) {
            activityClass = LandingPage_Surprise.class;
        }

        startActivity(new Intent(this, activityClass));
    }


}

