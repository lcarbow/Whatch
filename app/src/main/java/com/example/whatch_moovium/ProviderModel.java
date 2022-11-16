package com.example.whatch_moovium;

import android.widget.Switch;

public class ProviderModel {
    String providerName;
    boolean switchStatus;

    public ProviderModel(String providerName, boolean switchStatus) {
        this.providerName = providerName;
        this.switchStatus = switchStatus;
    }

    public String getProviderName() {
        return providerName;
    }

    public boolean getSwitchStatus(){
        return switchStatus;
    }

    public void setSwitchStatus(boolean switchStatus) {
        this.switchStatus = switchStatus;
    }
}
