package com.example.sitcinemax;

import java.io.Serializable;

public class Movies implements Serializable {
    String MovieName, MovieDescription, MovieRating, PosterUrl, MovieShort, SeatLayout, TrailerURL, Details, ScreenDate;
    boolean IsScreening;

    public Movies() {
    }

    public Movies(String movieName, String movieDescription, String movieRating, String posterUrl, String movieShort, boolean isScreening, String SeatLayout, String TrailerURL, String Details, String ScreenDate) {
        MovieName = movieName;
        MovieDescription = movieDescription;
        MovieRating = movieRating;
        PosterUrl = posterUrl;
        MovieShort = movieShort;
        IsScreening = isScreening;
        this.SeatLayout = SeatLayout;
        this.ScreenDate = ScreenDate;
        this.TrailerURL = TrailerURL;
        this.Details = Details;
    }

    public String getSeatLayout() {
        return SeatLayout;
    }

    public void setSeatLayout(String seatLayout) {
        SeatLayout = seatLayout;
    }

    public String getMovieName() {
        return MovieName;
    }

    public void setMovieName(String movieName) {
        MovieName = movieName;
    }

    public String getMovieDescription() {
        return MovieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        MovieDescription = movieDescription;
    }

    public String getMovieRating() {
        return MovieRating;
    }

    public void setMovieRating(String movieRating) {
        MovieRating = movieRating;
    }

    public String getPosterUrl() {
        return PosterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        PosterUrl = posterUrl;
    }

    public String getMovieShort() {
        return MovieShort;
    }

    public void setMovieShort(String movieShort) {
        MovieShort = movieShort;
    }

    public boolean isScreening() {
        return IsScreening;
    }

    public void setScreening(boolean screening) {
        IsScreening = screening;
    }

    public String getTrailerURL() {
        return TrailerURL;
    }

    public void setTrailerURL(String trailerURL) {
        TrailerURL = trailerURL;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getScreenDate() {
        return ScreenDate;
    }

    public void setScreenDate(String screenDate) {
        ScreenDate = screenDate;
    }
}
