package com.example.whatch_moovium.API_Interface;

import com.example.whatch_moovium.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiTools {

    //parses Movie object from movie json from a discover request and similarRequest
    static void movieParser(Movie movie, JSONObject jsonMovie) throws JSONException {
        //make new movie object
        movie.setTitle(jsonMovie.getString("title"));
        movie.setId(jsonMovie.getInt("id"));
        movie.setDescription(jsonMovie.getString("overview"));
        movie.setRating(jsonMovie.getDouble("vote_average"));
        //add genre ids
        JSONArray jsonGenres = jsonMovie.getJSONArray("genre_ids");
        for (int j = 0; j < jsonGenres.length(); j++) {
            movie.addGenre(jsonGenres.getInt(j));
        }
        movie.setPoster(jsonMovie.getString("poster_path"));
        movie.setBackdrop(jsonMovie.getString("backdrop_path"));
        movie.setReleaseDate(jsonMovie.getString("release_date"));
        movie.setOriginal_language(jsonMovie.getString("original_language"));

    }

    //parses Movie object from movie json from a movie request
    static void movieParserMovie(Movie movie, JSONObject jsonMovie) throws JSONException {
        //make new movie object
        movie.setTitle(jsonMovie.getString("title"));
        movie.setId(jsonMovie.getInt("id"));
        movie.setDescription(jsonMovie.getString("overview"));
        movie.setRating(jsonMovie.getDouble("vote_average"));
        //add genre ids
        JSONArray jsonGenres = jsonMovie.getJSONArray("genres");
        for (int j = 0; j < jsonGenres.length(); j++) {
            movie.addGenre(jsonGenres.getJSONObject(j).getInt("id"));
        }
        movie.setPoster(jsonMovie.getString("poster_path"));
        movie.setBackdrop(jsonMovie.getString("backdrop_path"));
        movie.setReleaseDate(jsonMovie.getString("release_date"));
        movie.setOriginal_language(jsonMovie.getString("original_language"));

    }
/*
    //convert Id to string
    public String convertProviderIdToName(int providerId) {
        String providerName = "";
        switch(providerId) {
            case 1:
                providerName = "Amazon Prime";
                break;
            case 2:
                providerName = "Hulu";
                break;
            case 3:
                providerName = "Netflix";
                break;
            case 4:
                providerName = "Disney+";
                break;
            // Add more cases as needed
            default:
                providerName = "Unknown";
                break;
        }
        return providerName;
    }

    //convert string to id
    public int convertProviderNameToId(String providerName) {
        int providerId = 0;
        switch(providerName.toLowerCase()) {
            case "amazon prime":
                providerId = 1;
                break;
            case "hulu":
                providerId = 2;
                break;
            case "netflix":
                providerId = 3;
                break;
            case "disney+":
                providerId = 4;
                break;
            case "amazon prime":
                providerId = 1;
                break;
            case "hulu":
                providerId = 2;
                break;
            case "netflix":
                providerId = 3;
                break;
            case "disney+":
                providerId = 4;
                break;
            // Add more cases as needed
            default:
                providerId = 0;
                break;
        }
        return providerId;
    }*/


}
