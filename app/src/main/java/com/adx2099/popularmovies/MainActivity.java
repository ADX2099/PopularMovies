package com.adx2099.popularmovies;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adx2099.popularmovies.Models.Movie;
import com.adx2099.popularmovies.Utils.MoviesJonUtils;
import com.adx2099.popularmovies.Utils.NetworkUtils;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecylerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpView();
    }

    private void setUpView() {
        mRecylerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecylerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
    }
    //----------------------------MENUs-------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //----------------------------DATA FEED-------------------------------------------------------
    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecylerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public void updateMovies(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrder = prefs.getString(getString(R.string.pref_sort_order_key), getString((R.string.pref_sort_order_most_popular)));

        new FetchMovieTask().execute(sortOrder);
    }

    private class FetchMovieTask extends AsyncTask<String, String, List<Movie>>{
        private final String LOG_TAG_FETCH = FetchMovieTask.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }


        @Override
        protected List<Movie> doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String movieJsonStr = null;

            URL moviesRequestUrl = NetworkUtils.buildUrl(params[0]);
            try{
                String jsonMoviesResponse = NetworkUtils.getResponseFromHttpUrl(moviesRequestUrl);
                List moviesList = MoviesJonUtils.getMovieDataFromJson(jsonMoviesResponse);

                return moviesList;
            }catch(Exception ex){
                ex.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(List<Movie> moviesData) {

            if (moviesData != null) {
               // showWeatherDataView();
                //mAdapter.setWeatherData(moviesData);
            } else {
                showErrorMessage();
            }
        }
    }




}
