package com.example.whatch_moovium.Presenter;

import static org.junit.Assert.*;

import android.content.Context;
import android.content.Intent;

import com.example.whatch_moovium.API_Interface.ApiInterface;
import com.example.whatch_moovium.API_Interface.Interfaces;
import com.example.whatch_moovium.Contract;

import org.junit.Test;
import org.junit.runner.manipulation.Ordering;
import org.mockito.Mockito;

public class SurprisePresenterTest {

    @Test
    public void onButtonClick() throws Exception {
        /*
        // Erstellen von Mock-Objekten für die benötigten Klassen und Interfaces
        Contract.ILandingViewSurprise mockLandingView = Mockito.mock(Contract.ILandingViewSurprise.class);
        Context mockContext = Mockito.mock(Context.class);
        Intent mockIntent = Mockito.mock(Intent.class);

        // Einrichten von Mockito, um ein Mock-Context-Objekt zurückzugeben, wenn die getContext()-Methode aufgerufen wird
        Mockito.when(mockLandingView.getContext()).thenReturn(mockContext);

        // Einrichten von Mockito, um ein Mock-Intent-Objekt zurückzugeben, wenn die new Intent()-Methode aufgerufen wird
        //Mockito.when(mockIntent.new(Mockito.any(Context.class), Mockito.any(Class.class))).thenReturn(mockIntent);

        // Erstellen einer Instanz von SurprisePresenter und Aufruf der onButtonClick()-Methode
        SurprisePresenter presenter = new SurprisePresenter(mockLandingView);
        presenter.onButtonClick();

        // Überprüfen, ob die startActivity()-Methode des Mock-Context-Objekts aufgerufen wurde
        Mockito.verify(mockContext).startActivity(mockIntent);

         */
    }

    @Test
    public void getMovieListFromApi() throws Exception{
        // Erstellen von Mock-Objekten für die benötigten Klassen und Interfaces
        Contract.ILandingViewSurprise mockLandingView = Mockito.mock(Contract.ILandingViewSurprise.class);
        ApiInterface mockApiInterface = Mockito.mock(ApiInterface.class);

        // Einrichten von Mockito, um ein Mock-Context-Objekt zurückzugeben, wenn die getContext()-Methode aufgerufen wird
        Context mockContext = Mockito.mock(Context.class);
        Mockito.when(mockLandingView.getContext()).thenReturn(mockContext);

        // Erstellen einer Instanz von SurprisePresenter und Aufruf der getMovieListFromApi()-Methode
        SurprisePresenter presenter = new SurprisePresenter(mockLandingView);
        presenter.getMovieListFromApi();

        // Überprüfen, ob die getDiscover()-Methode des Mock-ApiInterface-Objekts aufgerufen wurde
        Mockito.verify(mockApiInterface).getDiscover(Mockito.anyString(), Mockito.anyBoolean(), Mockito.anyList(), Mockito.any(Interfaces.apiDiscoverCallback.class));

    }

    @Test
    public void receiveDiscover() throws Exception{
    }
}