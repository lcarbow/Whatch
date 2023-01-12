package com.example.whatch_moovium.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.DatabaseHandler;
import com.example.whatch_moovium.Presenter.MoodPresenter;
import com.example.whatch_moovium.R;

public class MoodDispatcher extends Activity implements Contract.ILandingViewMood{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backgroundblack);
        SharedPreferences prefs = getSharedPreferences("X", Context.MODE_PRIVATE);
        DatabaseHandler db = new DatabaseHandler(this);


        MoodPresenter presenter = new MoodPresenter( this);


        if(prefs.getBoolean("dailyClicked", false)){
            Log.i("dailyClicked", "go to moviesugg");
            if(db.moodSize(prefs.getString("tableName", "NULL"))==0){
                presenter.toMoodSuggestion(prefs.getString("tableName", "NULL"));
            }
            else {
                presenter.getSimilarMovieListFromApi(prefs.getString("tableName", "NULL"));
            }
        } else{
            Intent iM = new Intent(this, LandingPage_Mood.class);
            startActivity(iM);
        }

    }
    @Override
    public Context getContext(){return this;}
}
