package com.example.whatch_moovium;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    String title;
    int id;
    int imdbID;
    String description;
    Double rating;
    List<String> genre = new ArrayList<>();
    //ENUM?
    String poster;
    String backdrop;
    String releaseDate;
    List<Integer> streaming;
    //ENUM?
    String original_language;


    public Movie(String title, int id, String description, Double rating, List<String> genre, String poster, String backdrop, String releaseDate, List<Integer> streaming, String original_language) {
        this.title = title;
        this.id = id;
        this.description = description;
        this.rating = rating;
        this.genre = genre;
        this.poster = poster;
        this.backdrop = backdrop;
        this.releaseDate = releaseDate;
        this.streaming = streaming;
        this.original_language = original_language;
    }

    public Movie() {
    }



    //setter & getter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getGenre() {
        String genresString = "";
        for (String genre : this.genre) {
            genresString += genre + " ";
        }
        return genresString;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public void addGenre(int genreID) {
        this.genre.add(Integer.toString(genreID));
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Integer> getStreaming() {
        return streaming;
    }

    public void setStreaming(List<Integer> streaming) {
        this.streaming = streaming;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }
}
