package com.adx2099.popularmovies.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adx2099.popularmovies.Models.Movie;
import com.adx2099.popularmovies.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "movieObj";
    private Movie movieData;
    private TextView title_tv,year_tv,rate_tv,overview_tv;
    private ImageView poster_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
         movieData = intent.getExtras().getParcelable(EXTRA_POSITION);
        setUpView();
        populateUI(movieData);
    }


    private void setUpView() {
        title_tv = (TextView) findViewById(R.id.title_tv);
        year_tv = (TextView) findViewById(R.id.year_tv);
        rate_tv = (TextView) findViewById(R.id.rate_tv);
        overview_tv = (TextView) findViewById(R.id.overview_tv);
        poster_iv = (ImageView) findViewById(R.id.poster_iv);
    }
    private void populateUI(Movie movieData) {
        title_tv.setText(movieData.originalTitle);
        year_tv.setText(movieData.releaseDate);
        rate_tv.setText(movieData.userRating);
        overview_tv.setText(movieData.overview);
        poster_iv = (ImageView) findViewById(R.id.poster_iv);
        Picasso.get()
                .load(movieData.moviePosterImageThumbnail)
                .into(poster_iv, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        // moviesAdapterViewHolder.mErrorImage.setVisibility(View.VISIBLE);
                    }
                });

    }
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    

}
