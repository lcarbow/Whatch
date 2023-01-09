package com.example.whatch_moovium.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.Presenter.ProviderAdapter;
import com.example.whatch_moovium.Presenter.ProviderPresenter;
import com.example.whatch_moovium.View.LandingPage_Surprise;

public class Dispatcher extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Class<?> activityClass;
        SharedPreferences prefs =

        try {
            SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
            activityClass = Class.forName(
                    prefs.getString("lastActivity", LandingPage_Surprise.class.getName()));
        } catch(ClassNotFoundException ex) {
            activityClass = LandingPage_Surprise.class;
        }

        startActivity(new Intent(this, activityClass));
    }


}

