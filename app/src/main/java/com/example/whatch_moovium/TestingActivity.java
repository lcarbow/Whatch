package com.example.whatch_moovium;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TestingActivity extends AppCompatActivity implements API_Interface.apiInterfaceCallback {

    private TextView testOutput;
    private TextView testOutputProviders;
    private TextView testOutputFiltered;

    static final String TAG = "UserLogging";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing);

        testOutput = findViewById(R.id.testOutput);
        testOutputProviders = findViewById(R.id.testOutputProviders);
        testOutputFiltered = findViewById(R.id.testOutputFiltered);

        Context context = getApplicationContext();
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        //toast.show();

        API_Interface myAPI_Interface = new API_Interface(this);
        myAPI_Interface.setCallback(this);

        //myAPI_Interface.getRandom(testOutput, testOutputProviders, testOutputFiltered);
        List<Integer> providerList = new ArrayList<>();
        providerList.add(8);
        providerList.add(337);
        myAPI_Interface.getDiscover("popularity.desc", true, providerList);

    }

    @Override
    public void deliverRequest(List<Movie> filteredMovieList) {
        testOutput.setText("");
        for (Movie movie : filteredMovieList) {
            testOutput.append(movie.getTitle() + " " + movie.getGenre() + "\n");
        }
    }
}
