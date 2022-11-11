package com.example.whatch_moovium;

import android.os.Handler;
import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Model implements Contract.Model {

    // array list of strings from which
    // random strings will be selected
    // to display in the activity
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
        }
        listener.onFinished(arrayList.get(index));
        index++;
        Log.i("userdebug","ich werde ausgeführt in getNextCourse in Model");
    }

}
