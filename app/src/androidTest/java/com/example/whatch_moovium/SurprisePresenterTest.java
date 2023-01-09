package com.example.whatch_moovium;

import com.example.whatch_moovium.Presenter.SurprisePresenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;


@RunWith(RobolectricTestRunner.class)
public class SurprisePresenterTest {

    @Test
    public void testOnButtonClick() {
        // Erstelle eine Test-Activity
        TestActivity activity = Robolectric.buildActivity(TestActivity.class).create().get();

        // Erstelle eine Instanz von SurprisePresenter mit der Test-Activity als View
        SurprisePresenter presenter = new SurprisePresenter(activity);

        // Rufe die onButtonClick-Methode auf
        presenter.onButtonClick();

        // Überprüfe, ob eine neue Activity gestartet wurde
        Intent expectedIntent = new Intent(activity, MovieSuggestion.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
    }

    @Test
    public void testGetMovieListFromApi() throws Exception {
        // Erstelle eine Test-Activity
        TestActivity activity = Robolectric.buildActivity(TestActivity.class).create().get();

        // Erstelle eine Instanz von SurprisePresenter mit der Test-Activity als View
        SurprisePresenter presenter = new SurprisePresenter(activity);

        // Erstelle eine Mock-Implementierung von ApiInterface
        ApiInterface mockApiInterface = mock(ApiInterface.class);
        presenter.myAPI_Interface = mockApiInterface;

        // Definiere, was passieren soll, wenn getDiscover aufgerufen wird
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                // Rufe den Callback auf, der übergeben wurde
                Interfaces.apiDiscoverCallback callback = (Interfaces.apiDiscoverCallback) invocation.getArguments()[3];
                // Erstelle eine Beispiel-Liste von Filmen
                List<Movie> exampleMovieList = Arrays.asList(new Movie("Movie 1"), new Movie("Movie 2"), new Movie("Movie 3"));
                // Rufe den Callback mit der Beispiel-Liste auf
                callback.receiveDiscover(exampleMovieList);
                return null;
            }
        }).when(mockApiInterface).getDiscover(anyString(), anyBoolean(), anyList(), any(Interfaces.apiDiscoverCallback.class));

        // Rufe getMovieListFromApi auf
        presenter.getMovieListFromApi();

        // Überprüfe, ob StorageClass die korrekte Liste von Filmen enthält
        Model expectedModel = new Model(Arrays.asList(new Movie("Movie 1"), new Movie("Movie 2"), new Movie("Movie 3")));
        Model actualModel = StorageClass.getInstance().getMyModel();
        assertEquals(expectedModel, actualModel);
    }
}
