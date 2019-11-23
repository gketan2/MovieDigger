package com.moviedigger.moviedigger.ratemoviesrecycler;

public class ResultData {
    private String movieName;
    private int movieId;
    private String imdbRating;
    private String imageUrl;
    private String genres;
    private float userRating;

    public ResultData(String movieName, int movieId) {
        this.movieName = movieName;
        this.movieId = movieId;
    }
    public String getMovieName() {
        return movieName;
    }
    public int getMovieId() {
        return movieId;
    }
    public String getImdbRating() {
        return imdbRating;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public String getGenres() {
        return genres;
    }
    public float getUserRating(){
        return userRating;
    }
    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setGenres(String genres) {
        this.genres = genres;
    }
    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }
}
