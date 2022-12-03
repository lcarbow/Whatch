package com.example.whatch_moovium.Model;

import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.Presenter.ImageHelper;

import java.util.List;

public class Model implements Contract.IModelView {

    // array list of strings from which
    // random strings will be selected
    // to display in the activitymodel
    private String ParentItemTitle;
    private List <Movie> arrayList;


    //////////DIESE GANZE INDEX KACKE IN STORAGE CLASS!!! //////////
    private int index;
    /////////////////////////////////////////////////////////

    public Model(List<Movie> arrayList) {
        this.arrayList = arrayList;
        index = 0;


    }

    ///////////// TITLE AUSLAGERN ///////////////
    public Model(String parentItemTitle, List<Movie> arrayList) {
        ParentItemTitle = parentItemTitle;
        this.arrayList = arrayList;
        this.index = 0;
    }
    /////////////////////////////

    public List<Movie> getArrayList() {
        return arrayList;
    }

    public void setArrayList(List<Movie> arrayList) {
        this.arrayList = arrayList;
    }

    public String getParentItemTitle() {
        return ParentItemTitle;
    }

    public void setParentItemTitle(String parentItemTitle) {
        ParentItemTitle = parentItemTitle;
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

    @Override
    public void nextIndex() {
        this.index = index + 1;


    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int showIndex() {
        return index;
    }

}
