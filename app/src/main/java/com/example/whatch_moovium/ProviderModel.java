package com.example.whatch_moovium;

import android.widget.Switch;

public class ProviderModel {
    String providerName;
    Switch providerSwitch;
    int providerID;

    boolean providerStatus;

    public ProviderModel(String providerName, String providerID, Switch providerSwitch, boolean providerStatus) {
        this.providerName = providerName;
        this.providerSwitch = providerSwitch;
        this.providerStatus = providerStatus;
        this.providerID = Integer.parseInt(providerID);
    }

    public String getProviderName() {
        return providerName;
    }

    public Switch getProviderSwitch() {
        return providerSwitch;
    }

    public boolean getProviderStatus(){ return providerStatus; }

    public void setProviderStatus(boolean providerStatus) {
        this.providerStatus = providerStatus;
    }

    public int getProviderID() {
        return providerID;
    }
}
