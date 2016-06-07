package com.example.baresse.moviespop.themoviedb.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetail extends Movie {

    private long budget;

    private List<NamedValue> genres;

    private String homepage;

    @SerializedName("imdb_id")
    private String imdbId;

    @SerializedName("production_companies")
    private List<NamedValue> productionCompanies;

    @SerializedName("production_countries")
    private List<Country> productionCountries;

    private long revenue;

    private int runtime;

    @SerializedName("spoken_languages")
    private List<Language> spokenLanguages;

    private String status;

    private String tagline;

    private List<Trailer> trailers;

    private List<Review> reviews;

    @Override
    public String toString() {
        return "MovieDetail{" +
                "budget=" + budget +
                ", genres=" + genres +
                ", homepage='" + homepage + '\'' +
                ", imdbId='" + imdbId + '\'' +
                ", productionCompanies=" + productionCompanies +
                ", productionCountries=" + productionCountries +
                ", revenue=" + revenue +
                ", runtime=" + runtime +
                ", spokenLanguages=" + spokenLanguages +
                ", status='" + status + '\'' +
                ", tagline='" + tagline + '\'' +
                ", trailers=" + trailers +
                ", reviews=" + reviews +
                '}';
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public List<NamedValue> getGenres() {
        return genres;
    }

    public void setGenres(List<NamedValue> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public List<NamedValue> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<NamedValue> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public List<Country> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<Country> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public List<Language> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<Language> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
