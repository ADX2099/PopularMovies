package com.adx2099.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
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
import android.widget.Toast;

import com.adx2099.popularmovies.Models.Movie;
import com.adx2099.popularmovies.Utils.MoviesJonUtils;
import com.adx2099.popularmovies.Utils.NetworkUtils;
import com.adx2099.popularmovies.activities.DetailActivity;
import com.adx2099.popularmovies.activities.SettingsActivity;
import com.adx2099.popularmovies.adapters.MoviesAdapter;

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

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterOnClickHandler {

    private RecyclerView mRecylerView;
    private MoviesAdapter mMoviesAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private TextView mErrorMessageDisplay;
    private Context context = this;

    private static final String TAG = "ADX2099";

    private ProgressBar mLoadingIndicator;
    //-----------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpView();
        mMoviesAdapter = new MoviesAdapter(this);
        mRecylerView.setAdapter(mMoviesAdapter);
        loadMoviesData();

    }
    //-----------------------------------------------------------------------------------------
    private void setUpView() {
        mRecylerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecylerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        mRecylerView.setLayoutManager(mLayoutManager);
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
        Context context = this;
        switch (id){
            case R.id.action_settings:
                Class destinationClass = SettingsActivity.class;
                Intent intentSettings = new Intent(context,destinationClass);
                startActivity(intentSettings);
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

    private void showMovieDataView() {

        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecylerView.setVisibility(View.VISIBLE);
    }

    public void loadMoviesData(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrder = prefs.getString(getString(R.string.pref_sort_order_key), getString((R.string.pref_sort_order_most_popular)));

        new FetchMovieTask().execute(sortOrder);
    }

    @Override
    public void onClick(Movie selectedMovie) {
      Class destinationClass = DetailActivity.class;
      Intent detailIntent = new Intent(context, destinationClass);
      startActivity(detailIntent);
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
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (moviesData.size() > 0) {

               showMovieDataView();
                mMoviesAdapter.setMoviesData(moviesData);
            } else {
                showErrorMessage();
            }
        }
    }




}
