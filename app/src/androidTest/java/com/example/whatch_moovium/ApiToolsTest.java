package com.example.whatch_moovium;


import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.whatch_moovium.API_Interface.ApiTools;
import com.example.whatch_moovium.Model.Movie;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ApiToolsTest {

    @Test
    public void testApiToolsMovieParser() throws JSONException {

        //make movie json
        JSONObject jsonMovie = new JSONObject("{" +
                "'adult': false," +
                "'backdrop_path':'/bQXAqRx2Fgc46uCVWgoPz5L5Dtr.jpg'," +
                "'genre_ids':[28,14,878]," +
                "'id':436270," +
                "'original_language':'en'," +
                "'original_title':'Black Adam'," +
                "'overview':'Fast 5.000 Jahre nachdem er mit den allmächtigen Kräften der ägyptischen Götter ausgestattet und ebenso schnell wieder eingesperrt wurde, wird Black Adam aus seinem irdischen Grab befreit und ist bereit, seine einzigartige Form der Gerechtigkeit auf die moderne Welt loszulassen.'," +
                "'popularity':6579.615," +
                "'poster_path':'/af5jyYU0DcQeI7z1YpKPTIbgPKX.jpg'," +
                "'release_date':'2022-10-20'," +
                "'title':'Black Adam'," +
                "'video':false," +
                "'vote_average':7.3," +
                "'vote_count':2508" +
                "}"
        );

        //convert to movie object
        Movie movie = new Movie();
        ApiTools.movieParser(movie, jsonMovie);

        //compare strings
        Assert.assertEquals("Black Adam", movie.getTitle());
        Assert.assertEquals(436270, movie.getId());
        Assert.assertEquals("Fast 5.000 Jahre nachdem er mit den allmächtigen Kräften der ägyptischen Götter ausgestattet und ebenso schnell wieder eingesperrt wurde, wird Black Adam aus seinem irdischen Grab befreit und ist bereit, seine einzigartige Form der Gerechtigkeit auf die moderne Welt loszulassen.", movie.getDescription());
        Assert.assertEquals("Action, Fantasy, Science Fiction, ", movie.getGenre());
        Assert.assertEquals(7.3, movie.getRating(), 0.001);
        Assert.assertEquals("/af5jyYU0DcQeI7z1YpKPTIbgPKX.jpg", movie.getPoster());
        Assert.assertEquals("/bQXAqRx2Fgc46uCVWgoPz5L5Dtr.jpg", movie.getBackdrop());
        Assert.assertEquals("2022-10-20", movie.getReleaseDate());
        Assert.assertEquals("en", movie.getOriginal_language());

    }

    @Test
    public void testApiToolsMovieParserMovie() throws JSONException {

        //make movie json
        JSONObject jsonMovie = new JSONObject("{\n" +
                "  \"adult\": false,\n" +
                "  \"backdrop_path\": \"/jYEW5xZkZk2WTrdbMGAPFuBqbDc.jpg\",\n" +
                "  \"belongs_to_collection\": {\n" +
                "    \"id\": 726871,\n" +
                "    \"name\": \"Dune Filmreihe\",\n" +
                "    \"poster_path\": \"/c1AiZTXyyzmPOlTLSubp7CEeYj.jpg\",\n" +
                "    \"backdrop_path\": \"/iCFFmXkK5FdIzqZyyQQEdpkTo8C.jpg\"\n" +
                "  },\n" +
                "  \"budget\": 165000000,\n" +
                "  \"genres\": [\n" +
                "    {\n" +
                "      \"id\": 878,\n" +
                "      \"name\": \"Science Fiction\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 12,\n" +
                "      \"name\": \"Abenteuer\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"homepage\": \"https://www.dunemovie.com/\",\n" +
                "  \"id\": 438631,\n" +
                "  \"imdb_id\": \"tt1160419\",\n" +
                "  \"original_language\": \"en\",\n" +
                "  \"original_title\": \"Dune\",\n" +
                "  \"overview\": \"\\\"Dune\\\" erzählt die packende Geschichte des brillanten jungen Helden Paul Atreides, dem das Schicksal eine Rolle vorherbestimmt hat, von der er niemals geträumt hätte. Um die Zukunft seiner Familie und seines gesamten Volkes zu sichern, muss Paul auf den gefährlichsten Planeten des Universums reisen. Nur auf dieser Welt existiert ein wertvoller Rohstoff, der es der Menschheit ermöglichen könnte, ihr vollständiges geistiges Potenzial auszuschöpfen.  Doch finstere Mächte wollen die Kontrolle über die kostbare Substanz an sich reißen. Es entbrennt ein erbitterter Kampf, den nur diejenigen überleben werden, die ihre eigenen Ängste besiegen.\",\n" +
                "  \"popularity\": 100.375,\n" +
                "  \"poster_path\": \"/34Hr4bEzF30jd6sEvI4HihEUEI8.jpg\",\n" +
                "  \"production_companies\": [\n" +
                "    {\n" +
                "      \"id\": 923,\n" +
                "      \"logo_path\": \"/5UQsZrfbfG2dYJbx8DxfoTr2Bvu.png\",\n" +
                "      \"name\": \"Legendary Pictures\",\n" +
                "      \"origin_country\": \"US\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"production_countries\": [\n" +
                "    {\n" +
                "      \"iso_3166_1\": \"US\",\n" +
                "      \"name\": \"United States of America\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"release_date\": \"2021-09-08\",\n" +
                "  \"revenue\": 400671789,\n" +
                "  \"runtime\": 155,\n" +
                "  \"spoken_languages\": [\n" +
                "    {\n" +
                "      \"english_name\": \"Mandarin\",\n" +
                "      \"iso_639_1\": \"zh\",\n" +
                "      \"name\": \"普通话\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"english_name\": \"English\",\n" +
                "      \"iso_639_1\": \"en\",\n" +
                "      \"name\": \"English\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"Released\",\n" +
                "  \"tagline\": \"Jenseits der Angst erwartet uns das Schicksal\",\n" +
                "  \"title\": \"Dune\",\n" +
                "  \"video\": false,\n" +
                "  \"vote_average\": 7.841,\n" +
                "  \"vote_count\": 8013\n" +
                "}"
        );

        //convert to movie object
        Movie movie = new Movie();
        ApiTools.movieParserMovie(movie, jsonMovie);

        //compare strings
        Assert.assertEquals("Dune", movie.getTitle());
        Assert.assertEquals(438631, movie.getId());
        Assert.assertEquals("\"Dune\" erzählt die packende Geschichte des brillanten jungen Helden Paul Atreides, dem das Schicksal eine Rolle vorherbestimmt hat, von der er niemals geträumt hätte. Um die Zukunft seiner Familie und seines gesamten Volkes zu sichern, muss Paul auf den gefährlichsten Planeten des Universums reisen. Nur auf dieser Welt existiert ein wertvoller Rohstoff, der es der Menschheit ermöglichen könnte, ihr vollständiges geistiges Potenzial auszuschöpfen.  Doch finstere Mächte wollen die Kontrolle über die kostbare Substanz an sich reißen. Es entbrennt ein erbitterter Kampf, den nur diejenigen überleben werden, die ihre eigenen Ängste besiegen.", movie.getDescription());
        Assert.assertEquals("Science Fiction, Abenteuer, ", movie.getGenre());
        Assert.assertEquals(7.841, movie.getRating(), 0.001);
        Assert.assertEquals("/34Hr4bEzF30jd6sEvI4HihEUEI8.jpg", movie.getPoster());
        Assert.assertEquals("/jYEW5xZkZk2WTrdbMGAPFuBqbDc.jpg", movie.getBackdrop());
        Assert.assertEquals("2021-09-08", movie.getReleaseDate());
        Assert.assertEquals("en", movie.getOriginal_language());

    }

}
