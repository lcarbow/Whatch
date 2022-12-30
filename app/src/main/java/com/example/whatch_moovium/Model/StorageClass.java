package com.example.whatch_moovium.Model;

import android.util.Log;

import com.example.whatch_moovium.Provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StorageClass {

    ////// Singleton ///////////
    private static StorageClass instance = null;

    ///////// Generell ////////
    private Model myModel;


    //////// Genre ////////
    private List<Model> myModelList;
    private List<String> providerList;
    List<Model> itemList;
    List<String> genreList;
    private Movie actualMovie;
    private boolean ready;

    public StorageClass() {

        this.providerList = new ArrayList<>();
        this.myModelList = new ArrayList<>();
        this.itemList = new ArrayList<>();
        this.genreList = new ArrayList<>();
        this.ready = true;
    }

    public static StorageClass getInstance() {
        if (instance == null) instance = new StorageClass();
        return instance;
    }

    public Model getMyModel() {
        return myModel;
    }

    public void setMyModel(Model myModel) {
        this.myModel = myModel;
    }

    public List<String> getProviderList() {
        List<String> providerListInt = new ArrayList<>();

        for (String i : providerList) {
            providerListInt.add(i);
        }
        return providerListInt;
    }

    public void setProviderList(List<String> providerList) {
        this.providerList = providerList;
    }

    public Movie getActualMovie() {
        return actualMovie;
    }

    public void setActualMovie(Movie actualMovie) {
        this.actualMovie = actualMovie;
    }

    public void addMyModelList(Model model) {
        this.myModelList.add(model);
    }

    public void addProviderList(String providerName) {
        Log.i("funktioniert", "Liste vor Hinzufügen: " + providerList.size());
        this.providerList.add(providerName);
        Log.i("funktioniert", "Liste nach Hinzufügen: " + providerList.size());
    }

    public void removeProviderList(String providerName) {
        if (providerList.contains(providerName)){
            Log.i("funktioniert", "Liste vor Entfernung: " + providerList.size());
            this.providerList.remove(providerName);
            Log.i("funktioniert", "Liste nach Entfernung: " + providerList.size());
        }

    }

    public List<Model> getMyModelList() {
        return myModelList;
    }

    public List<Model> getItemList() {
        return itemList;
    }

    public void setItemList(List<Model> itemList) {
        this.itemList = itemList;
    }

    public List<String> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<String> genreList) {
        this.genreList = genreList;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public void resetSettingForGenreList() {
        this.myModelList.clear();
        this.genreList.clear();
        this.itemList.clear();
    }

}
