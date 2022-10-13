package com.example.whatch_moovium;

public class Movie {

    public Movie(String title) {
        this.title = title;
    }



    String title;
    /*
    String description;
    float rating;
    String genre;
    //ENUM?
    int img;
    String streaming;
    //ENUM?

     */


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
