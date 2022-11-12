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
    List<String> streaming = new ArrayList<>();
    //ENUM?
    String original_language;


    public Movie(String title, int id, String description, Double rating, List<String> genre, String poster, String backdrop, String releaseDate, List<String> streaming, String original_language) {
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
            String genreToAppend;

            switch(genre) {
                //Wollen wir uns die Bezeichnungen auch dynamisch liefern lassen?
                //https://api.themoviedb.org/3/genre/movie/list?api_key=f862a1abef6de0d1ca20c51abb9f51ab&language=de-DE
                case "28":

                    genreToAppend = "Action";

                    break;
                case "12":
                    genreToAppend = "Abenteuer";
                    break;
                case "16":
                    genreToAppend = "Animation";
                    break;
                case "35":
                    genreToAppend = "Komödie";
                    break;
                case "99":
                    genreToAppend = "Dokumentarfilm";
                    break;
                case "18":
                    genreToAppend = "Drama";
                    break;
                case "10751":
                    genreToAppend = "Familie";
                    break;
                case "14":
                    genreToAppend = "Fantasy";
                    break;
                case "36":
                    genreToAppend = "Historie";
                    break;
                case "27":
                    genreToAppend = "Horror";
                    break;
                case "10402":
                    genreToAppend = "Musik";
                    break;
                case "9648":
                    genreToAppend = "Mystery";
                    break;
                case "10749":
                    genreToAppend = "Liebesfilm";
                    break;
                case "878":
                    genreToAppend = "Science Fiction";
                    break;
                case "10770":
                    genreToAppend = "TV-Film";
                    break;
                case "53":
                    genreToAppend = "Thriller";
                    break;
                case "10752":
                    genreToAppend = "Kriegsfilm";
                    break;
                case "37":
                    genreToAppend = "Western";
                    break;
                default:
                    genreToAppend = "";
                    break;
            }

            genresString += genreToAppend + ", ";
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

    public String getStreaming() {
        /*
        //Log.i("userdebug",  this.streaming.get(0) + "ich werde ausgeführt in getStream");

        String streamingString = "";
        for (String stream : this.streaming) {
            Log.i("userdebug",  stream + "ich werde ausgeführt in getStream");

            String streamingToAppend;

            switch(stream) {
                case "8":
                    streamingToAppend = "Netflix";
                    Log.i("userdebug", streamingToAppend + "blaaaa");

                    break;
                case "337":
                    streamingToAppend = "Disney";
                    break;
                case "119":
                    streamingToAppend = "Prime Video";
                    break;
                default:
                    streamingToAppend = "";
                    break;
            }
            Log.i("userdebug", streamingString + "blaaaa");

            streamingString += streamingToAppend + ", ";
        }
        return streamingString;

         */
        return "hat Alex noch nicht gemacht";
    }

    public void setStreaming(List<String> streaming) {
        this.streaming = streaming;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }
}
