package com.example.whatch_moovium.Model;

import com.example.whatch_moovium.Provider;

import java.util.ArrayList;
import java.util.List;

public class StorageClass {

    ////// Singleton ///////////
    private static StorageClass instance = null;

    ///////// Generell ////////
    private Model myModel;


    //////// Genre ////////
    private List<Model> myModelList;
    private List<Provider> providerList;
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

    public List<Integer> getProviderList() {
        List<Integer> providerListInt = new ArrayList<>();

        for (Provider i : providerList) {
            providerListInt.add(i.getId());
        }
        return providerListInt;
    }

    public void setProviderList(List<Provider> providerList) {
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

    public void addProviderIdList(Provider i) {

        this.providerList.add(i);
    }

    public void removeProviderIdList(Provider i) {
        this.providerList.remove(i);
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

}
