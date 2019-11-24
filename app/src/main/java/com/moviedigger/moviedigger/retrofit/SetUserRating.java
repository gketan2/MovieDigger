package com.moviedigger.moviedigger.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SetUserRating {

    @SerializedName("responsecode")
    private int responsecode;
    @SerializedName("responsemessage")
    private String responsemessage;
    @SerializedName("ratings")
    private ArrayList<Float> rating = new ArrayList<>();
    @SerializedName("username")
    private String username;
    @SerializedName("tmdb_ids")
    private ArrayList<Integer> movieId = new ArrayList<>();

    public SetUserRating(String username, ArrayList<Float> rating, ArrayList<Integer> movieId){
        this.username = username;
        this.rating = rating;
        this.movieId = movieId;
    }

    public int getResponsecode() {
        return responsecode;
    }
    public String getResponsemessage() {
        return responsemessage;
    }

    public void setMovieId(ArrayList<Integer> movieId){
        this.movieId.clear();
        this.movieId.addAll(movieId);
    }
    public void setRating(ArrayList<Float> rating){
        this.rating.clear();
        this.rating.addAll(rating);
    }

}
