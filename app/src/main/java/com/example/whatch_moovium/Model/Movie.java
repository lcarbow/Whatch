package com.example.whatch_moovium.Model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
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
    Bitmap posterBitmap;
    String backdrop;
    String releaseDate;
    List<String> streaming = new ArrayList<>();
    //ENUM?
    String original_language;
    boolean imageLoaded = false;


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

    public Movie(Bitmap posterBitmap) {
        this.posterBitmap = posterBitmap;
    }

    public Bitmap getPosterBitmap() {
        return posterBitmap;
    }

    public void setPosterBitmap(Bitmap posterBitmap) {
        this.posterBitmap = posterBitmap;
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
                case "80":
                    genreToAppend = "Krimi";
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
        String streamingServices = "";

        for (String provider : this.streaming) {
            streamingServices += provider + ", ";
        }
        return streamingServices;

    }

    //checks if this Movie is available a specific Streaming service
    public boolean isAvailableAt(String service) {
        for (String currentService : streaming) {
            if (currentService.equalsIgnoreCase(service)) {return true;}
        }
        return false;
    }

    //add one streaming provider as string
    public void addStreaming(String watchProvider) {
        this.streaming.add(watchProvider);
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public int getImdbID() {
        return imdbID;
    }

    public void setImdbID(int imdbID) {
        this.imdbID = imdbID;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", id=" + id +
                ", imdbID=" + imdbID +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", genre=" + getGenre() +
                ", poster='" + poster + '\'' +
                ", backdrop='" + backdrop + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", streaming=" + streaming +
                ", original_language='" + original_language + '\'' +
                '}';
    }

    public boolean isImageLoaded() {
        return imageLoaded;
    }

    public void setImageLoaded(boolean imageLoaded) {
        this.imageLoaded = imageLoaded;
    }
}
