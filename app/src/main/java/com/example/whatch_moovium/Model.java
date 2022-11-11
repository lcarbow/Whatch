package com.example.whatch_moovium;

import android.os.Handler;
import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Model implements Contract.Model {

    private List <Movie> arrayList = Arrays.asList(
    new Movie("Godzilla",
            0,
            "Sie planten eine Vegas-Junggesellen-Party, die sie nie vergessen würden. Jetzt müssen sie unbedingt herausfinden, was genau schiefgelaufen ist. Wem gehört das Baby im Schrank der Caesars-Palace-Suite? Wie kommt der Tiger ins Badezimmer? Warum fehlt einem der Jungs ein Zahn? Und vor allem, wo ist der Bräutigam? Was die Jungs beim „Draufmachen“ so erleben, ist nichts im Vergleich zu den Kapriolen, die sie nüchtern veranstalten müssen. Sie sind gezwungen, all die schlimmen Entscheidungen der letzten Nacht zu rekonstruieren – eine nach der anderen.",
            83.0,
            "Komödie",
            "godzilla",
            "Netflix"),
    new Movie("Dune",
            0,
            "Sie planten eine Vegas-Junggesellen-Party, die sie nie vergessen würden. Jetzt müssen sie unbedingt herausfinden, was genau schiefgelaufen ist. Wem gehört das Baby im Schrank der Caesars-Palace-Suite? Wie kommt der Tiger ins Badezimmer? Warum fehlt einem der Jungs ein Zahn? Und vor allem, wo ist der Bräutigam? Was die Jungs beim „Draufmachen“ so erleben, ist nichts im Vergleich zu den Kapriolen, die sie nüchtern veranstalten müssen. Sie sind gezwungen, all die schlimmen Entscheidungen der letzten Nacht zu rekonstruieren – eine nach der anderen.",
            63.0,
            "Drama",
            "hangover",
            "Netflix")

    );


    public void getNextMovie(final OnFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("userdebug", "wird aufgerufen in model");

                listener.onFinished(getRandomMovie());
            }
        }, 1200);
    }
    // method to select random
    // string from the list of strings
    private Movie getRandomMovie() {
        Random random = new Random();
        int index = random.nextInt(arrayList.size());
        return arrayList.get(index);
    }


}
