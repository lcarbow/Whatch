package com.example.whatch_moovium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = new Intent(MainActivity.this,LandingPage_Surprise.class);
        //TODO Lasse shared Prefs
        startActivity(i);

    }
}