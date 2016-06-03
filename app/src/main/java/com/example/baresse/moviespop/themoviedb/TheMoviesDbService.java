package com.example.baresse.moviespop.themoviedb;


import com.example.baresse.moviespop.themoviedb.model.MoviesResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheMoviesDbService {

    String BASE_URL = "http://api.themoviedb.org/3/";
    String BASE_IMG_URL = "http://image.tmdb.org/t/p/";

    String API_KEY_PARAM = "api_key";

    @GET("movie/popular")
    Call<MoviesResult>
    getMoviesOrderedByPopularity(@Query(API_KEY_PARAM) String apiKey);

    @GET("movie/top_rated")
    Call<MoviesResult>
    getMoviesOrderedByRating(@Query(API_KEY_PARAM) String apiKey);
}
