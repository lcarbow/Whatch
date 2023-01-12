package com.example.whatch_moovium.Presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;

import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.R;
import com.example.whatch_moovium.View.LandingPage_Genres;
import com.example.whatch_moovium.View.LandingPage_Mood;
import com.example.whatch_moovium.View.LandingPage_Surprise;
import com.example.whatch_moovium.View.MoodDispatcher;

public class BottomNavPresenter implements Contract.IBottomNavPresenter{

    private Contract.IBottomNavContext bottomNavContext;

    public BottomNavPresenter(Contract.IBottomNavContext context) {
        this.bottomNavContext = context;
    }

    @Override
    public void onItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mood:
                Intent iM = new Intent(bottomNavContext.getContextForNav(), MoodDispatcher.class);
                bottomNavContext.getContextForNav().startActivity(iM);
                break;
            case R.id.genres:
                Intent iG = new Intent(bottomNavContext.getContextForNav(), LandingPage_Genres.class);
                bottomNavContext.getContextForNav().startActivity(iG);
                break;
            case R.id.surprise:
                Intent iS = new Intent(bottomNavContext.getContextForNav(), LandingPage_Surprise.class);
                bottomNavContext.getContextForNav().startActivity(iS);
                break;
        }
    }
}
