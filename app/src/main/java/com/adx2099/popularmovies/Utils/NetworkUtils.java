package com.adx2099.popularmovies.Utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    //------------------------------DATA URI ----------------------
    private static final String SCHEME_PARAM = "https";
    private static final String AUTHORITY_PARAM = "api.themoviedb.org";
    private static final String PATH1_PARAM = "3";
    private static final String PATH2_PARAM = "movie";
    private static final String PATH3_PARAM = "popular";
    private static final String SORT_BY_PARAM = "sort_by";
    private static final String API_KEY_PARAM = "api_key";
    private static final String LANGUAGE_PARAM = "language";

    private static String SORT_BY = "popularity.desc";

    //--------------------------------------------------------------------------------------------
    public static URL buildUrl(String... params) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEME_PARAM)
                .authority(AUTHORITY_PARAM)
                .appendPath(PATH1_PARAM)
                .appendPath(PATH2_PARAM)
                .appendPath(PATH3_PARAM)
                .appendQueryParameter(SORT_BY_PARAM, params[0])//.appendQueryParameter(SORT_BY_PARAM, SORT_BY)
                .appendQueryParameter(API_KEY_PARAM, PopularMovConstants.API_KEY)
                .appendQueryParameter(LANGUAGE_PARAM, PopularMovConstants.LANGUAGE);

        String myUrl = builder.build().toString();
        Log.d("ADX2099URL", myUrl);
        URL url = null;
        try {
            url = new URL(myUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    //--------------------------------------------------------------------------------------------
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}
