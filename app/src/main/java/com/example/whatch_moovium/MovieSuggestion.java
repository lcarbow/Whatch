package com.example.whatch_moovium;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieSuggestion extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_suggestion);

        TextView titleView = findViewById(R.id.movieTitle);
        TextView descriptionView = findViewById(R.id.movieDesc);
        TextView genreView = findViewById(R.id.movieGenre);
        TextView ratingView = findViewById(R.id.movieRating);
        TextView streamingView = findViewById(R.id.movieStreaming);

        //Test Movie
        List<Movie> movieList=new ArrayList<>();

        Movie hangover = new Movie(
            "hangover",
            "Sie planten eine Vegas-Junggesellen-Party, die sie nie vergessen würden. Jetzt müssen sie unbedingt herausfinden, was genau schiefgelaufen ist. Wem gehört das Baby im Schrank der Caesars-Palace-Suite? Wie kommt der Tiger ins Badezimmer? Warum fehlt einem der Jungs ein Zahn? Und vor allem, wo ist der Bräutigam? Was die Jungs beim „Draufmachen“ so erleben, ist nichts im Vergleich zu den Kapriolen, die sie nüchtern veranstalten müssen. Sie sind gezwungen, all die schlimmen Entscheidungen der letzten Nacht zu rekonstruieren – eine nach der anderen.",
            83,
            "Komödie",
            0,
            "Netflix"
        );

        Movie godzilla = new Movie(
                "Godzilla",
                "Sie planten eine Vegas-Junggesellen-Party, die sie nie vergessen würden. Jetzt müssen sie unbedingt herausfinden, was genau schiefgelaufen ist. Wem gehört das Baby im Schrank der Caesars-Palace-Suite? Wie kommt der Tiger ins Badezimmer? Warum fehlt einem der Jungs ein Zahn? Und vor allem, wo ist der Bräutigam? Was die Jungs beim „Draufmachen“ so erleben, ist nichts im Vergleich zu den Kapriolen, die sie nüchtern veranstalten müssen. Sie sind gezwungen, all die schlimmen Entscheidungen der letzten Nacht zu rekonstruieren – eine nach der anderen.",
                83,
                "Komödie",
                0,
                "Netflix"
        );
        Movie dune = new Movie(
                "Dune",
                "Sie planten eine Vegas-Junggesellen-Party, die sie nie vergessen würden. Jetzt müssen sie unbedingt herausfinden, was genau schiefgelaufen ist. Wem gehört das Baby im Schrank der Caesars-Palace-Suite? Wie kommt der Tiger ins Badezimmer? Warum fehlt einem der Jungs ein Zahn? Und vor allem, wo ist der Bräutigam? Was die Jungs beim „Draufmachen“ so erleben, ist nichts im Vergleich zu den Kapriolen, die sie nüchtern veranstalten müssen. Sie sind gezwungen, all die schlimmen Entscheidungen der letzten Nacht zu rekonstruieren – eine nach der anderen.",
                83,
                "Komödie",
                0,
                "Netflix"
        );

        movieList.add(hangover);
        movieList.add(godzilla);
        movieList.add(dune);

        Collections.shuffle(movieList);

        titleView.setText(movieList.get(0).getTitle());

        descriptionView.setText(movieList.get(0).getDescription());
        genreView.setText(movieList.get(0).getGenre());
        ratingView.setText(Integer.toString((int) movieList.get(0).getRating()) + "% Benutzerbewertung");
        streamingView.setText("Als Stream verfügbar auf " + movieList.get(0).getStreaming());






    }
}