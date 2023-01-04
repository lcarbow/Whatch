package com.example.whatch_moovium;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: Changing the value of the boolean");
        SharedPreferences prefs = context.getSharedPreferences("X", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("dailyClicked", false).apply();
    }
}
