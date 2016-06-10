package com.example.baresse.moviespop.themoviedb;


import com.example.baresse.moviespop.themoviedb.model.MovieDetail;
import com.example.baresse.moviespop.themoviedb.model.MoviesResult;
import com.example.baresse.moviespop.themoviedb.model.ReviewsResult;
import com.example.baresse.moviespop.themoviedb.model.TrailersResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMoviesDbService {

    String BASE_URL = "http://api.themoviedb.org/3/";
    String BASE_IMG_URL = "http://image.tmdb.org/t/p/";

    String API_KEY_PARAM = "api_key";
    String PAGE_PARAM = "page";

    @GET("movie/popular")
    Call<MoviesResult>
    getMoviesOrderedByPopularity(@Query(API_KEY_PARAM) String apiKey, @Query(PAGE_PARAM) int page);

    @GET("movie/top_rated")
    Call<MoviesResult>
    getMoviesOrderedByRating(@Query(API_KEY_PARAM) String apiKey, @Query(PAGE_PARAM) int page);

    @GET("movie/{id}")
    Call<MovieDetail>
    getMovieDetail(@Path("id") long movieId, @Query(API_KEY_PARAM) String apiKey);

    @GET("movie/{id}/videos")
    Call<TrailersResult>
    getMovieTrailers(@Path("id") long movieId, @Query(API_KEY_PARAM) String apiKey);

    @GET("movie/{id}/reviews")
    Call<ReviewsResult>
    getMovieReviews(@Path("id") long movieId, @Query(API_KEY_PARAM) String apiKey);
}
