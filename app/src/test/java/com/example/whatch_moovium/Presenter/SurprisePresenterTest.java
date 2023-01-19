package com.example.whatch_moovium.Presenter;

import android.content.Context;

import com.example.whatch_moovium.API_Interface.ApiHandler;
import com.example.whatch_moovium.API_Interface.Interfaces;
import com.example.whatch_moovium.Contract;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SurprisePresenterTest {

    @Test
    public void onButtonClick() throws Exception {
        /*
        // Erstellen von Mock-Objekten für die benötigten Klassen und Interfaces
        Contract.ILandingViewSurprise mockLandingView = Mockito.mock(Contract.ILandingViewSurprise.class);
        Context mockContext = Mockito.mock(Context.class);

        // Einrichten von Mockito, um ein Mock-Context-Objekt zurückzugeben, wenn die getContext()-Methode aufgerufen wird
        //Mockito.when(mockLandingView.getContext()).thenReturn(mockContext);

        // Erstellen einer Instanz von SurprisePresenter und Aufruf der onButtonClick()-Methode
        SurprisePresenter presenter = new SurprisePresenter(mockLandingView);
        presenter.onButtonClick();

        // Überprüfen, ob die startActivity()-Methode des Mock-Context-Objekts aufgerufen wurde
        Mockito.verify(mockContext).startActivity(Mockito.any(Intent.class));

         */
    }

    @Test
    public void getMovieListFromApi() throws Exception{


        // Erstellen von Mock-Objekten für die benötigten Klassen und Interfaces
        Contract.ILandingViewSurprise mockLandingView = Mockito.mock(Contract.ILandingViewSurprise.class);
        ApiHandler mockApiHandler = Mockito.mock(ApiHandler.class);

        // Einrichten von Mockito, um ein Mock-Context-Objekt zurückzugeben, wenn die getContext()-Methode aufgerufen wird
        Context mockContext = Mockito.mock(Context.class);
        Mockito.when(mockLandingView.getContext()).thenReturn(mockContext);

        // Erstellen einer Instanz von SurprisePresenter und Aufruf der getMovieListFromApi()-Methode
        SurprisePresenter presenter = new SurprisePresenter(mockLandingView);
        presenter.getMovieListFromApi();

        // Überprüfen, ob die getDiscover()-Methode des Mock-ApiInterface-Objekts aufgerufen wurde
        Mockito.verify(mockApiHandler).getDiscover(Mockito.anyString(), Mockito.anyBoolean(), Mockito.anyList(), Mockito.any(Interfaces.apiDiscoverCallback.class));




    }

    @Test
    public void receiveDiscover() throws Exception{
    }
}