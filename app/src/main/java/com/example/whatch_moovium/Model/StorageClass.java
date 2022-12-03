package com.example.whatch_moovium.Model;

import java.util.ArrayList;
import java.util.List;

public class StorageClass {

    private static StorageClass instance = null;
    private Model myModel;
    private List<Model> myModelList;
    private List<Integer> providerList;
    List<Model> itemList;
    List<String> genreList;
    private Movie actualMovie;

    public StorageClass() {

        this.providerList = new ArrayList<>();
        this.myModelList = new ArrayList<>();
        this.itemList = new ArrayList<>();
        this.genreList = new ArrayList<>();
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
        return providerList;
    }

    public void setProviderList(List<Integer> providerList) {
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
}
