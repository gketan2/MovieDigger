package com.moviedigger.moviedigger.retrofit;

import com.google.gson.annotations.SerializedName;

public class TokenAuth {
    @SerializedName("username")
    private String username;
    @SerializedName("token")
    private String token;
    @SerializedName("responsecode")
    private int responsecode;
    @SerializedName("responsemessage")
    private String responsemessage;

    public TokenAuth(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public int getResponsecode() {
        return responsecode;
    }

    public String getResponseMessage() {
        return responsemessage;
    }
}
