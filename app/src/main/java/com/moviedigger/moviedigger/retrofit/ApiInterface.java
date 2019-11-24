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

    @POST("profile_status/")
    Call<ProfileStatus> profileSatus(@Body ProfileStatus status);

    @POST("register_movies/")
    Call<GenresList> setGenres(@Body GenresList list);

    @POST("register_movies/")
    Call<SetUserRating> rateMovies(@Body SetUserRating list);

    @POST("get_popular_movies/")
    Call<MoviesList> getMovieList(@Body MoviesList list);

    @POST("recommend_movies_to_user/")
    Call<MoviesList> recommendedMovies(@Body MoviesList list);

    @POST("get_user_movies/")
    Call<MoviesList> history(@Body MoviesList list);

}
