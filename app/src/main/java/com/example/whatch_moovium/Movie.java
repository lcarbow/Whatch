package com.example.whatch_moovium;

public class Movie {

    public Movie(String title, String description, int rating, String genre, String img, String streaming) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.genre = genre;
        this.img = img;
        this.streaming = streaming;
    }

    String title;

    String description;
    int rating;
    String genre;
    //ENUM?
    String img;
    String streaming;
    //ENUM?


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStreaming() {
        return streaming;
    }

    public void setStreaming(String streaming) {
        this.streaming = streaming;
    }
}
