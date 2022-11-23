package com.example.whatch_moovium;

import android.os.Handler;
import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Model implements Contract.Model {

    // array list of strings from which
    // random strings will be selected
    // to display in the activitymodel
    private List <Movie> arrayList;
    int index;

    public Model(List<Movie> arrayList) {
        this.arrayList = arrayList;
        index = 0;
    }

    public List<Movie> getArrayList() {
        return arrayList;
    }

    public void setArrayList(List<Movie> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    // this method will invoke when
    // user clicks on the button
    // Soll auch für NEXT-Button genutzt werden
    public void getNextMovie(final OnFinishedListener listener) {
        if(index >= arrayList.size()) {
            index = 0;
        } else if(index < 0) {
            index = arrayList.size()-1;
        }
        listener.onFinished(arrayList.get(index));
        index++;
    }

    public void getBeforeMovie(final OnFinishedListener listener) {
        if(index >= arrayList.size()) {
            index = 0;
        } else if(index < 0) {
            index = arrayList.size()-1;
        }
        listener.onFinished(arrayList.get(index));
        index--;
    }

}
