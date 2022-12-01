package com.example.whatch_moovium;

import java.util.List;

public class WatchlistPresenter implements Contract.WatchlistPresenter, Contract.ModelView.OnFinishedListener,Interfaces.apiDiscoverCallback, Interfaces.apiBackdropCallback, Interfaces.apiPosterCallback{
    private Contract.LandingViewWatchlist landingPageWatchlist;
    private Contract.ModelView model;

    ApiInterface myApiInterface;
    List<Movie> movieList;
    List<Model> itemList;

    int index;

    public WatchlistPresenter(Contract.LandingViewWatchlist landingPageWatchlist){
        this.landingPageWatchlist = landingPageWatchlist;
    }

    @Override
    public void getMovieListFromApi() {

        myApiInterface = new ApiInterface(landingPageWatchlist.getContext());


    }
}
