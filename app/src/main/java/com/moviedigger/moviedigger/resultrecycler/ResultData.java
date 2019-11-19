package com.moviedigger.moviedigger.resultrecycler;

public class ResultData {
    String movieName;
    int movieId;
    String imdbRating;
    String imageUrl;

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
    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
