package com.example.sitcinemax;

public class Movies {
    String MovieName, MovieDescription, MovieRating, PosterUrl, MovieShort, SeatLayout;
    boolean IsScreening;

    public Movies() {
    }

    public Movies(String movieName, String movieDescription, String movieRating, String posterUrl, String movieShort, boolean isScreening, String SeatLayout) {
        MovieName = movieName;
        MovieDescription = movieDescription;
        MovieRating = movieRating;
        PosterUrl = posterUrl;
        MovieShort = movieShort;
        IsScreening = isScreening;
        this.SeatLayout = SeatLayout;
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
}
