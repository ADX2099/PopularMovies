package com.adx2099.popularmovies.Utils;

import android.os.Parcel;

import com.adx2099.popularmovies.Models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class MoviesJonUtils {

    public static List<Movie> getMovieDataFromJson(String MovieJsonStr) throws JSONException {
        JSONObject moviesJson = new JSONObject(MovieJsonStr);
        List items = new ArrayList<>();
        final String OWM_RESULTS = "results";
        final String OWM_ID = "id";
        final String OWM_POSTER = "poster_path";
        final String OWM_TITLE = "title";
        final String OWM_OVERVIEW = "overview";
        final String OWM_DATE = "release_date";
        final String OWN_VOTE_AVERAGE = "vote_average";
        final String URL_IMAGE_BASE = "http://image.tmdb.org/t/p/";
        final String SIZE_IMAGE = "w185" + "/";


        items.clear();
        JSONArray moviesArray = moviesJson.getJSONArray(OWM_RESULTS);

        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movieJson = moviesArray.getJSONObject(i);
            /*

           items.add(new Movie(movieJson.getString(OWM_TITLE)
                    , URL_IMAGE_BASE + SIZE_IMAGE + movieJson.getString(OWM_POSTER)
                    , movieJson.getString(OWM_OVERVIEW)
                    , movieJson.getString(OWN_VOTE_AVERAGE)
                    , movieJson.getString(OWM_DATE)
                    , movieJson.getInt(OWM_ID)));*/


        }


        return items;
    }


}
