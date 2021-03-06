package com.moviedigger.moviedigger.retrofit;

import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("responsecode")
    private int responsecode;
    @SerializedName("responsemessage")
    private String responsemessage;
    @SerializedName("token")
    private String token;
    @SerializedName("profilestatus")
    private int profilestatus;

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
    public int getresponsecode() {
        return responsecode;
    }
    public String getresponsemessage() {
        return responsemessage;
    }
    public String getToken(){
        return token;
    }
    public int getProfileStatus(){
        return profilestatus;
    }



}
