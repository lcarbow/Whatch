package com.example.whatch_moovium.Presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Switch;

import androidx.preference.PreferenceManager;

import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.Model.ProviderModel;
import com.example.whatch_moovium.Model.StorageClass;
import com.example.whatch_moovium.R;

import java.util.ArrayList;
import java.util.List;

public class ProviderPresenter {

    ArrayList<ProviderModel> possibleProviders = new ArrayList<>();
    private Contract.LandingViewProvider landingPageProvider;

    public ProviderPresenter(Contract.LandingViewProvider landingPageProvider){
        this.landingPageProvider = landingPageProvider;
    }

    public void onPageLoaded(){
        setupProvider();
    }
    public void setupProvider(){
        String[] providerNames = landingPageProvider.getContext().getResources().getStringArray(R.array.possible_providers);
        List<String> providerList = new ArrayList<>();

        StorageClass.getInstance().resetSettingForGenreList();

        for (int i = 0; i < providerNames.length; i++){
            Switch newSwitch = new Switch(landingPageProvider.getContext());

            SharedPreferences getSwitchPrefs = landingPageProvider.getContext().getSharedPreferences("X", Context.MODE_PRIVATE);
            boolean newBool = getSwitchPrefs.getBoolean("value"+i, true);

            newSwitch.setChecked(newBool);
            possibleProviders.add(new ProviderModel(providerNames[i], newSwitch, newBool));
            if (newBool){
                providerList.add(providerNames[i]);
            }
        }

        StorageClass.getInstance().setProviderList(providerList);

        //Test
        for (int e = 0; e < providerList.size(); e++){
            Log.i("providerLog", "" + providerList.get(e).toString());
        }

    }

    public void switchFlip(int position, boolean switchState){
        ProviderModel providerStatus = possibleProviders.get(position);
        SharedPreferences switchPref = landingPageProvider.getContext().getSharedPreferences("X", Context.MODE_PRIVATE);
        SharedPreferences.Editor switchEditor = switchPref.edit();
        if (switchState){
            String currentPosition = providerStatus.getProviderName();
            StorageClass.getInstance().addProviderList(currentPosition);

            providerStatus.setProviderStatus(true);
            switchEditor.putBoolean("value"+position, true);
            switchEditor.apply();
        } else {
            String currentPosition = providerStatus.getProviderName();
            StorageClass.getInstance().removeProviderList(currentPosition);
            providerStatus.setProviderStatus(false);
            switchEditor.putBoolean("value"+position, false);
            switchEditor.apply();
        }
    }
}
