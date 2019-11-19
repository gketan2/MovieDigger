package com.moviedigger.moviedigger.retrofit;

import com.google.gson.annotations.SerializedName;

public class ProfileStatus {

    @SerializedName("username")
    private String username;
    @SerializedName("responsecode")
    private int responsecode;
    @SerializedName("responsemessage")
    private String responsemessage;
    @SerializedName("profilestatus")
    private int profilestatus;

    public ProfileStatus(String username) {
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

    public int getProfilestatus() {
        return profilestatus;
    }

}
