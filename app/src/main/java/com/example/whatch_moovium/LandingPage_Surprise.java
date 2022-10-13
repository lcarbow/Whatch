package com.example.whatch_moovium;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LandingPage_Surprise extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page_surprise);

        //Test Movie

        Movie hangover = new Movie("Hangover");
        Movie godzilla = new Movie("Godzilla");
        Movie dune = new Movie("Dune");


        List<Movie> movieList=new ArrayList<>();
        movieList.add(hangover);
        movieList.add(godzilla);
        movieList.add(dune);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.shuffle(movieList);

                Toast.makeText(LandingPage_Surprise.this,
                        movieList.get(0).getTitle(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}