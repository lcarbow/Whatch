package com.example.whatch_moovium.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.whatch_moovium.R;
import com.example.whatch_moovium.TestingActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent i = new Intent(MainActivity.this,LandingPage_Surprise.class);
        Intent i = new Intent(MainActivity.this, TestingActivity.class);
        //TODO Lasse shared Prefs
        startActivity(i);
        //
    }
}