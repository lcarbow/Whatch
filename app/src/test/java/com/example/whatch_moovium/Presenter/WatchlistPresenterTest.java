package com.example.whatch_moovium.Presenter;

import com.example.whatch_moovium.API_Interface.ApiInterface;
import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.DatabaseHandler;
import com.example.whatch_moovium.Model.Movie;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class WatchlistPresenterTest {
    private WatchlistPresenter watchlistPresenter;
    private Contract.LandingViewWatchlist landingPageWatchlist;
    private ApiInterface myApiInterface;
    private DatabaseHandler dbHandler;

    @Before
    public void setUp() {
        landingPageWatchlist = mock(Contract.LandingViewWatchlist.class);
        myApiInterface = mock(ApiInterface.class);
        dbHandler = mock(DatabaseHandler.class);

        watchlistPresenter = new WatchlistPresenter(landingPageWatchlist);
        watchlistPresenter.myApiInterface = myApiInterface;
        watchlistPresenter.dbHandler = dbHandler;
    }

    /**
     * Getmovielistfromapi should call the database to get the watchlist
     */
    @Test
    public void getMovieListFromApiShouldCallDatabaseToGetWatchlist() {
        // create mock of the DatabaseHandler
        DatabaseHandler dbHandlerMock = mock(DatabaseHandler.class);
        // inject the mock into the WatchlistPresenter
        watchlistPresenter.dbHandler = dbHandlerMock;

        watchlistPresenter.getMovieListFromApi();
        // verify that the getWatchlist method is called
        verify(dbHandlerMock).getWatchlist();
    }

    /**
     * Getmovielistfromapi should call the api to get the movies
     */
    @Test
    public void getMovieListFromApiShouldCallApiToGetMovies() {
        List<Integer> watchList = new ArrayList<>();
        watchList.add(1);
        watchList.add(2);
        watchList.add(3);

        when(dbHandler.getWatchlist()).thenReturn(watchList);

        watchlistPresenter.getMovieListFromApi();

        verify(myApiInterface).getWatchlist(watchList, watchlistPresenter);
    }

    /**
     * Receivewatchlist should save the movies in a list
     */
    @Test
    public void receiveWatchlistShouldSaveMoviesInAList() {
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie());
        movieList.add(new Movie());
        movieList.add(new Movie());

        watchlistPresenter.receiveWatchlist(movieList);

        assertEquals(movieList, watchlistPresenter.getMovieList());
    }

    /**
     * Receiveposter should save the poster in a movie
     */
    @Test
    public void receivePosterShouldSavePosterInAMovie() {
        Movie movie = new Movie();
        movie.setPosterBitmap(null);
        watchlistPresenter.getMovieList().add(movie);
        watchlistPresenter.index = 0;

        watchlistPresenter.receivePoster(null);

        assertNotNull(watchlistPresenter.getMovieList().get(0).getPosterBitmap());
    }

    /**
     * Receivewatchlist should call the api to get the posters
     */
    @Test
    public void receiveWatchlistShouldCallApiToGetPosters() {
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie());
        movieList.add(new Movie());
        movieList.add(new Movie());

        watchlistPresenter.receiveWatchlist(movieList);

        verify(myApiInterface).getPoster(movieList.get(0).getPoster(), watchlistPresenter);
        verify(myApiInterface).getPoster(movieList.get(1).getPoster(), watchlistPresenter);
        verify(myApiInterface).getPoster(movieList.get(2).getPoster(), watchlistPresenter);
    }
}