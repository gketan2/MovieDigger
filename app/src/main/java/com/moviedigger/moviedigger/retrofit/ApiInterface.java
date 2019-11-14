package com.moviedigger.moviedigger.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    String BASE_URL = "http://192.168.100.6:5000/";

    @POST("verify/")
    Call<LoginData> signin(@Body LoginData signin);

    @POST("signup/")
    Call<LoginData> signup(@Body LoginData signup);

    @POST("token_auth/")
    Call<TokenAuth> tokenAuth(@Body TokenAuth token);

}
