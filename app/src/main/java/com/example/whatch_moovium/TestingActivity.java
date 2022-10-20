package com.example.whatch_moovium;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

    }

    @Override
    public void displayMovie(List<Movie> filteredMovieList) {
        testOutputFiltered.setText("");
        for (Movie movie : filteredMovieList) {
            testOutputFiltered.append(movie.getTitle() + "\n");
        }
    }
}
