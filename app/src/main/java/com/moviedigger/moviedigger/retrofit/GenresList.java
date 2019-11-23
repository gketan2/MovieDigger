package com.moviedigger.moviedigger.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GenresList {
    @SerializedName("username")
    private String username;
    @SerializedName("responsecode")
    private int responsecode;
    @SerializedName("responsemessage")
    private String responsemessage;
    @SerializedName("genres")
    private ArrayList<String> genres;

    public GenresList(String username, ArrayList<String> genres){
        this.username = username;
        this.genres = genres;
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
    public ArrayList<String> getGenres() {
        return genres;
    }
}
