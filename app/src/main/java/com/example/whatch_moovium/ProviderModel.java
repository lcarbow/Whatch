package com.example.whatch_moovium;

import android.widget.Switch;

public class ProviderModel {
    String providerName;
    Switch providerSwitch;

    boolean providerStatus;

    public ProviderModel(String providerName, Switch providerSwitch, boolean providerStatus) {
        this.providerName = providerName;
        this.providerSwitch = providerSwitch;
        this.providerStatus = providerStatus;
    }

    public String getProviderName() {
        return providerName;
    }

    public boolean getProviderStatus(){ return providerStatus; }

    public void setProviderStatus(boolean providerStatus) {
        this.providerStatus = providerStatus;
    }
}
