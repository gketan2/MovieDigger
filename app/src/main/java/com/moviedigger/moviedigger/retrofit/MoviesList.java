package com.moviedigger.moviedigger.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MoviesList {

    @SerializedName("username")
    private String username;
    @SerializedName("responsecode")
    private int responsecode;
    @SerializedName("responsemessage")
    private String responsemessage;
    @SerializedName("names")
    private ArrayList<String> movieNameList;
    @SerializedName("tmdb_ids")
    private ArrayList<Integer> movieId;
    @SerializedName("num_movies")
    private int num_movies;
    @SerializedName("genre")
    private String genre;

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
    public ArrayList<String> getMovieNameList() {
        return movieNameList;
    }
    public ArrayList<Integer> getMovieId() {
        return movieId;
    }

    public void setNum_movies(int num_movies) {
        this.num_movies = num_movies;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
}
