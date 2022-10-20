package com.example.whatch_moovium;

public class Movie {

    public Movie(String title, int id, String description, Double rating, String genre, String img, String streaming) {
        this.title = title;
        this.id = id;
        this.description = description;
        this.rating = rating;
        this.genre = genre;
        this.img = img;
        this.streaming = streaming;
    }

    String title;
    int id;
    String description;
    Double rating;
    String genre;
    //ENUM?
    String img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
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
