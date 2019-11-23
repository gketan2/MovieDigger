package com.moviedigger.moviedigger.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SetUserRating {
    @SerializedName("rating")
    private ArrayList<Float> rating = new ArrayList<>();
    @SerializedName("username")
    private String username;
    @SerializedName("tmdb_ids")
    private ArrayList<Integer> movieId = new ArrayList<>();

    public SetUserRating(ArrayList<Float> rating, ArrayList<Integer> movieId){
        this.rating = rating;
        this.movieId = movieId;
    }

}
