package com.example.whatch_moovium;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TestingActivity extends AppCompatActivity implements Interfaces.apiDiscoverCallback, Interfaces.apiBackdropCallback, Interfaces.apiPosterCallback {

    private TextView testOutput;
    private TextView testOutputProviders;
    private ImageView posterView;
    private ImageView backdropView;

    private ApiInterface myAPI_Interface;

    static final String TAG = "UserLogging";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing);

        //set outputs
        testOutput = findViewById(R.id.testOutput);
        testOutputProviders = findViewById(R.id.testOutputProviders);
        posterView = findViewById(R.id.posterView);
        backdropView = findViewById(R.id.backdropView);

        //make api interface
        myAPI_Interface = new ApiInterface(this);
        //set callback
        //myAPI_Interface.setCallback(this);


        List<Integer> providerList = new ArrayList<>();
        providerList.add(8);
        providerList.add(337);
        //myAPI_Interface.getDiscover("popularity.desc", true, providerList);

    }

    @Override
    public void receiveDiscover(List<Movie> movieList) {
        testOutput.setText("");
        for (Movie movie : movieList) {
            testOutput.append(movie.getTitle() + " " + movie.getGenre() + "\n");
        }

        //get poster
        myAPI_Interface.getPoster(movieList.get(2).getPoster(), this);
        //get Backdrop
        myAPI_Interface.getBackdrop(movieList.get(2).getBackdrop(), this);
    }

    @Override
    public void receivePoster(Bitmap img) {
        posterView.setImageBitmap(img);
    }

    @Override
    public void receiveBackdrop(Bitmap img) {
        backdropView.setImageBitmap(img);
    }

}
