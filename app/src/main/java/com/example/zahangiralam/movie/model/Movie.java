package com.example.zahangiralam.movie.model;

import android.graphics.Bitmap;
import java.io.Serializable;

/**
 * Created by Zahangir Alam on 2017-12-27.
 */

public class Movie implements Serializable{
    private int movieId;
    private String movieName;
    private double movieRatting;
    private Bitmap movieImage;
    private String movieDescription;
    private String movieGenre;
    //private User user;

    public Movie() {
    }

    public Movie(int movieId, String movieName, double movieRatting, Bitmap movieImage, String movieDescription, String movieGenre) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieRatting = movieRatting;
        this.movieImage = movieImage;
        this.movieDescription = movieDescription;
        //this.user = user;
        this.movieGenre = movieGenre;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public double getMovieRatting() {
        return movieRatting;
    }

    public void setMovieRatting(double movieRatting) {
        this.movieRatting = movieRatting;
    }

    public Bitmap getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(Bitmap movieImage) {
        this.movieImage = movieImage;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    /*public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }*/

    public String getMovieGenre() {
        return movieGenre;
    }

    public void setMovieGenre(String genre) {
        this.movieGenre = genre;
    }
}
