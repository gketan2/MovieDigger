package com.moviedigger.moviedigger.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesList {

    @SerializedName("username")
    private String username;
    @SerializedName("responsecode")
    private int responsecode;
    @SerializedName("responsemessage")
    private String responsemessage;
    @SerializedName("movie_name")
    private List<String> movieNameList;
    @SerializedName("movie_id")
    private List<Integer> movieId;

    public MoviesList(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    public int getResponsecode() {
        return responsecode;
    }
    public String getResponsemessage() {
        return responsemessage;
    }
    public List<String> getMovieNameList() {
        return movieNameList;
    }
    public List<Integer> getMovieId() {
        return movieId;
    }
}
