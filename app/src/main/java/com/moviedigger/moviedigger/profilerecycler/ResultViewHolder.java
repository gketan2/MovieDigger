package com.moviedigger.moviedigger.profilerecycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moviedigger.moviedigger.R;

public class ResultViewHolder extends RecyclerView.ViewHolder {
    TextView movie_name;
    TextView rating;
    TextView userRating;
    TextView genres;
    ImageView movieImage;
    RatingBar ratingBar;
    TextView user_rating;

    public ResultViewHolder(@NonNull View itemView) {
        super(itemView);
        movie_name = itemView.findViewById(R.id.movie_name);
        rating = itemView.findViewById(R.id.imdb_rate);
        genres = itemView.findViewById(R.id.genres);
        user_rating = itemView.findViewById(R.id.user_rating);
        movieImage = itemView.findViewById(R.id.movie_image);
        ratingBar = itemView.findViewById(R.id.movie_rating);
    }
}
