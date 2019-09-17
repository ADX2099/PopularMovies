package com.adx2099.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adx2099.popularmovies.Models.Movie;
import com.adx2099.popularmovies.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {

    private List<Movie> mMovieData;
    private final MoviesAdapterOnClickHandler mClickHandler;

    public interface MoviesAdapterOnClickHandler{
        void onClick(Movie selectedMovie);
    }

    public MoviesAdapter(MoviesAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }



    @NonNull
    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.card_movie;
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem,viewGroup,shouldAttachToParentImmediately);

        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoviesAdapterViewHolder moviesAdapterViewHolder, int position) {
        Movie moviePosition = mMovieData.get(position);
        moviesAdapterViewHolder.movie_name.setText(moviePosition.originalTitle);
        Log.d("ADXImage", moviePosition.moviePosterImageThumbnail);
        Picasso.get()
                .load(moviePosition.moviePosterImageThumbnail)
                .into(moviesAdapterViewHolder.imagen_view_card, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                       // moviesAdapterViewHolder.mErrorImage.setVisibility(View.VISIBLE);
                    }
                });


    }

    @Override
    public int getItemCount() {
        return (mMovieData != null ? mMovieData.size() : 0);
    }

    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView imagen_view_card;
        public final TextView movie_name;

        public MoviesAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            imagen_view_card =  itemView.findViewById(R.id.imagen_view_card);
            movie_name =  itemView.findViewById(R.id.movie_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie moviePosition = mMovieData.get(adapterPosition);
            mClickHandler.onClick(moviePosition);
        }
    }

    public void setMoviesData(List<Movie> moviesData){

        mMovieData = moviesData;

        notifyDataSetChanged();

    }
}
