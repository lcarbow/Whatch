package com.example.whatch_moovium.Presenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.whatch_moovium.API_Interface.ApiInterface;
import com.example.whatch_moovium.API_Interface.Interfaces;
import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.DatabaseHandler;
import com.example.whatch_moovium.Model.Model;
import com.example.whatch_moovium.Model.Movie;
import com.example.whatch_moovium.Model.StorageClass;
import com.example.whatch_moovium.MyBroadcastReceiver;
import com.example.whatch_moovium.View.LandingPage_MoodSuggestion;
import com.example.whatch_moovium.View.MovieSuggestion;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MoodPresenter implements Contract.IMoodPresenter, Interfaces.apiDiscoverCallback, Interfaces.apiSimilarCallback{

    private Contract.ILandingViewMood landingPageView;
    private ApiInterface myAPI_Interface;
    private SharedPreferences prefs;
    private boolean dailyClicked = false;
    private DatabaseHandler db;


    public MoodPresenter(Contract.ILandingViewMood landingPageView) {
        this.landingPageView = landingPageView;
        myAPI_Interface = new ApiInterface(landingPageView.getContext());
        prefs = landingPageView.getContext().getSharedPreferences("X", Context.MODE_PRIVATE);
        this.db = new DatabaseHandler(landingPageView.getContext());
    }

    @Override
    public void onPageLoaded() {
        //TODO: Wenn am heutigen Tag bereits ein Gefühl gewählt wurde, direkt zur MovieSugg bevor diese Seite geladen wird.
        //TODO: if dailyClicked [sharedPrefs]

        // Get a reference to the AlarmManager
        AlarmManager alarmManager = (AlarmManager) landingPageView.getContext().getSystemService(Context.ALARM_SERVICE);

        // Set the alarm to trigger at 1am every day
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long triggerTime = calendar.getTimeInMillis();

        // Set up an Intent to be broadcast by the AlarmManager
        Intent intent = new Intent(landingPageView.getContext(), MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(landingPageView.getContext(), 0, intent, 0);

        // Set the alarm
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, triggerTime, AlarmManager.INTERVAL_DAY, pendingIntent);


        if (prefs.getBoolean("dailyClicked", false)){
            Log.i("dailyClicked", "is skipped to movies, or NOT?");
            if(db.moodSize(prefs.getString("tableName", "NULL"))==0){
                toMoodSuggestion(prefs.getString("tableName", "NULL"));
            }
            else {
                getSimilarMovieListFromApi(prefs.getString("tableName", "NULL"));
                toMovieSuggestion();
            }
        }
        //TODO else:
        else {
            Log.i("dailyClicked", "is NOT skipped to movies");
            Log.i("dailyClicked", String.valueOf(dailyClicked));
            getRandomMovieListFromApi();
        }
    }

    @Override
    public void onEmojiClick(String tablename) {

        prefs.edit().putBoolean("dailyClicked", true).commit();
        prefs.edit().putString("tableName", tablename).commit();
        Log.i("dailyClicked", "boolean set true");
        Log.i("dailyClicked", String.valueOf(prefs.getBoolean("dailyClicked", false)));
        //TODO: in DB nach gewählter mood suchen
        if (db.checkIfThree(tablename)){
            //TODO: else:
            getSimilarMovieListFromApi(tablename);
            toMovieSuggestion();
        }
        else{
            //TODO: if DB.gewählte_mood.size < 3:
            toMoodSuggestion(tablename);
        }

    }

    @Override
    public void getRandomMovieListFromApi() {
        //TODO: API_Request:Random für vorzuschlagene Filme
        //TODO: Bitte aber mal was anderes als wir bei SurpriseMovie haben.
        myAPI_Interface.getDiscover("popularity.desc", true, StorageClass.getInstance().getProviderList(), this);
    }

    @Override
    public void getSimilarMovieListFromApi(String tablename) {
        //TODO: API_Request:getSimilar mit bereits in DB enthaltenen Filmen
        myAPI_Interface.getSimilar(db.getMoodlist(tablename), true, StorageClass.getInstance().getProviderList(), 5, this);
    }

    @Override
    public void receiveSimilar(List<Movie> filteredMovieList) {
        Collections.shuffle(filteredMovieList);
        StorageClass.getInstance().setMyModel(new Model(filteredMovieList));
        Log.i("receiveSimilar", "Geladen");
    }

    @Override
    public void receiveDiscover(List<Movie> filteredMovieList) {
        Collections.shuffle(filteredMovieList);
        StorageClass.getInstance().setMyModel(new Model(filteredMovieList));
    }

    @Override
    public void toMovieSuggestion() {
        if (landingPageView != null) {
            Intent i = new Intent(landingPageView.getContext(), MovieSuggestion.class);
            landingPageView.getContext().startActivity(i);
        }
    }

    @Override
    public void toMoodSuggestion(String tablename) {
        Intent i = new Intent(landingPageView.getContext(), LandingPage_MoodSuggestion.class);
        i.putExtra("tablename", tablename);
        landingPageView.getContext().startActivity(i);
    }






}
