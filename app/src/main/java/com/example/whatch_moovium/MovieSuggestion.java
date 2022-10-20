package com.example.whatch_moovium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieSuggestion extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_suggestion);

        TextView descriptionView = findViewById(R.id.movieDesc);
        TextView genreView = findViewById(R.id.movieGenre);
        TextView ratingView = findViewById(R.id.movieRating);
        TextView streamingView = findViewById(R.id.movieStreaming);
        ImageView movieImage = findViewById(R.id.movieImg);

        Button buttonShare = findViewById(R.id.button_share);
        Button buttonAdd = findViewById(R.id.button_add);
        Button buttonSeen = findViewById(R.id.button_seen);

        //Test Movie
        List<Movie> movieList=new ArrayList<>();

        Movie hangover = new Movie(
            "hangover",
            "Sie planten eine Vegas-Junggesellen-Party, die sie nie vergessen würden. Jetzt müssen sie unbedingt herausfinden, was genau schiefgelaufen ist. Wem gehört das Baby im Schrank der Caesars-Palace-Suite? Wie kommt der Tiger ins Badezimmer? Warum fehlt einem der Jungs ein Zahn? Und vor allem, wo ist der Bräutigam? Was die Jungs beim „Draufmachen“ so erleben, ist nichts im Vergleich zu den Kapriolen, die sie nüchtern veranstalten müssen. Sie sind gezwungen, all die schlimmen Entscheidungen der letzten Nacht zu rekonstruieren – eine nach der anderen.",
            83,
            "Komödie",
            "hangover",
            "Netflix"
        );

        Movie godzilla = new Movie(
                "Godzilla",
                "In einem japanischen Atomkraftwerk, in dem der amerikanische Ingenieur Joe Brody und seine Frau Sandra beschäftigt sind, kommt es zu einer Katastrophe. Noch Jahre später versucht Joe, der als einer der wenigen überlebt hat, die wahren Ursachen des Unglücks aufzuklären. Und seine Intuition gibt ihm Recht. Zusammen mit seinem Sohn Ford und dem Wissenschaftler Ichiro Serizawa findet er heraus, dass ein riesiges Monster für die Katastrophe von damals verantwortlich war. Und jetzt schlägt es wieder zu! Das Militär ist machtlos. Kann der sagenumwobene Godzilla helfen?",
                63,
                "Drama",
                "godzilla",
                "Netflix"
        );

        movieList.add(hangover);
        movieList.add(godzilla);

        Collections.shuffle(movieList);

        String uri = "@drawable/" + movieList.get(0).getImg();  // where myresource (without the extension) is the file

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());

        Drawable res = getResources().getDrawable(imageResource);

        movieImage.setImageDrawable(res);
        descriptionView.setText(movieList.get(0).getDescription());
        genreView.setText(movieList.get(0).getGenre());
        ratingView.setText(Integer.toString((int) movieList.get(0).getRating()) + "% Benutzerbewertung");
        streamingView.setText("Als Stream verfügbar auf " + movieList.get(0).getStreaming());

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Lasse Database
                Toast.makeText(MovieSuggestion.this,
                        "Zur Watchlist hinzugefügt!", Toast.LENGTH_SHORT).show();
            }
        });

        buttonSeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Lasse Database
                Toast.makeText(MovieSuggestion.this,
                        "Zur Gesehenlist hinzugefügt!", Toast.LENGTH_SHORT).show();
            }
        });

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Schau, welchen coolen Film ich gefunden habe: " + movieList.get(0).getTitle());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);

            }
        });




    }
}