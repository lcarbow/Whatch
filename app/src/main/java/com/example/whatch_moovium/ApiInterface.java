package com.example.whatch_moovium;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ApiInterface {

    private RequestQueue mQueue;
    //api key
    private String apiKey = "f862a1abef6de0d1ca20c51abb9f51ab";
    apiDiscoverCallback myDiscoverCallback;
    apiCallbackBackdrop myBackdropCallback;
    apiCallbackPoster myPosterCallback;

    //constructor
    public ApiInterface(Context context) {
        mQueue = Volley.newRequestQueue(context);
    }

    public void setCallback(Presenter context) {
        //set discover callback
        this.myDiscoverCallback = (apiDiscoverCallback) context;

        //set poster callback
        this.myPosterCallback = (apiCallbackPoster) context;

        //set backdrop callback
        this.myBackdropCallback = (apiCallbackBackdrop) context;
    }


    public void getDiscover(String sort, boolean flatrate, List<Integer> providers) {

        //make discover request


        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + apiKey + "&language=de-DE&region=DE&sort_by=" + sort + "&include_adult=false&include_video=false&page=1";

        //if only show from own streaming services
        if (flatrate) {
            //make provider string
            String providersString = "";
            for (Integer providerINT : providers) {
                providersString += Integer.toString(providerINT);
                providersString += "%7C";
            }
            url += "&with_watch_providers=" + providersString + "&watch_region=DE&with_watch_monetization_types=flatrate";
        }

        //make discover request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonMovielist) {

                        //process json

                        //create movie list
                        List<Movie> movieList = new ArrayList<>();

                        try {
                            //get array of movies from response
                            JSONArray results = jsonMovielist.getJSONArray("results");

                            //make movie array
                            for (int i = 0; i < results.length(); i++) {
                                //get json for single movie
                                JSONObject jsonMovie = results.getJSONObject(i);
                                //parse movie form json
                                Movie movie = movieParser(jsonMovie);
                                //add movie to list
                                movieList.add(movie);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //hand movies to callback
                        getDiscoverCallback(movieList);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);

    }

    public void getDiscover(String sort, boolean flatrate, List<Integer> provider, String genre) {

    }

    private void getDiscoverCallback(List<Movie> movieList) {
        //call callback function
        myDiscoverCallback.receiveDiscover(movieList);
    }

    //parses Movie object from movie json
    private Movie movieParser(JSONObject jsonMovie) throws JSONException {
        //make new movie object
        Movie movie = new Movie();
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

        return movie;
    }

    public void getPoster(String imgPath) {

        String url = "https://image.tmdb.org/t/p/w780" + imgPath;

        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        getPosterCallback(response);
                    }
                }, 10000, 10000, null, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(imageRequest);
    }

    private void getPosterCallback(Bitmap img) {
        myPosterCallback.receivePoster(img);
    }

    public void getBackdrop(String imgPath) {

        String url = "https://image.tmdb.org/t/p/original" + imgPath;

        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        getBackdropCallback(response);
                    }
                }, 10000, 10000, null, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(imageRequest);
    }

    private void getBackdropCallback(Bitmap img) {
        myBackdropCallback.receiveBackdrop(img);
    }




/*

    public void getWatchProvider(int movieID, String title, Movie movie, List<Movie> filteredMovieList) {

        String url = "https://api.themoviedb.org/3/movie/" + movieID + "/watch/providers?api_key=" + apiKey;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject JSONwatchProviders) {

                        String providers = "";
                        List<String> watchProviders = new ArrayList<>();

                        try {

                            JSONObject results = JSONwatchProviders.getJSONObject("results");
                            JSONObject DE = results.getJSONObject("DE");
                            JSONArray flatrate = DE.getJSONArray("flatrate");

                            for (int i = 0; i < flatrate.length(); i++) {
                                JSONObject provider = flatrate.getJSONObject(i);
                                providers += provider.getString("provider_name") + " ";
                                watchProviders.add(provider.getString("provider_name"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //call callback
                        //watchProviderCallback(movie, watchProviders, filteredMovieList);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);

    }

*/


    public interface apiDiscoverCallback {
        void receiveDiscover(List<Movie> filteredMovieList);
    }

    public interface apiCallbackPoster {
        void receivePoster(Bitmap img);
    }

    public interface apiCallbackBackdrop {
        void receiveBackdrop(Bitmap img);
    }

}