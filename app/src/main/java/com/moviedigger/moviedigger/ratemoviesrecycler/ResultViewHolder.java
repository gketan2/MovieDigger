package com.moviedigger.moviedigger.ratemoviesrecycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.moviedigger.moviedigger.R;

public class ResultViewHolder extends RecyclerView.ViewHolder {
    TextView movie_name;
    TextView rating;
    TextView genres;
    ImageView movieImage;
    AppCompatRatingBar ratingBar;

    public ResultViewHolder(@NonNull View itemView) {
        super(itemView);
        movie_name = itemView.findViewById(R.id.movie_name);
        rating = itemView.findViewById(R.id.imdb_rate);/////////////////
        genres = itemView.findViewById(R.id.genres);//////////////////
        movieImage = itemView.findViewById(R.id.movie_image);
        ratingBar = itemView.findViewById(R.id.movie_rating);
    }
}
