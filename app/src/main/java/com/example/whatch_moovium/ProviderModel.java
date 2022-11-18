package com.example.whatch_moovium;

import android.widget.Switch;

public class ProviderModel {
    String providerName;

    Switch providerSwitch;

    public ProviderModel(String providerName, Switch providerSwitch) {
        this.providerName = providerName;
        this.providerSwitch = providerSwitch;
    }

    public String getProviderName() {
        return providerName;
    }

    public Switch getProviderSwitch() {
        return providerSwitch;
    }

}
