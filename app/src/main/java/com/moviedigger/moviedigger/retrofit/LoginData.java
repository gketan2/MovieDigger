package com.moviedigger.moviedigger.retrofit;

import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("responsecode")
    private String responsecode;
    @SerializedName("responsemessage")
    private String responsemessage;

    public LoginData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getresponsecode() {
        return responsecode;
    }

    public String getresponsemessage() {
        return responsemessage;
    }



}
