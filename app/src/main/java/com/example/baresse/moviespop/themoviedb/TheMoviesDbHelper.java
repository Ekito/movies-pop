package com.example.baresse.moviespop.themoviedb;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.baresse.moviespop.BuildConfig;
import com.example.baresse.moviespop.R;
import com.example.baresse.moviespop.data.Favorites;
import com.example.baresse.moviespop.themoviedb.model.Movie;
import com.example.baresse.moviespop.themoviedb.model.MovieDetail;
import com.example.baresse.moviespop.themoviedb.model.MoviesResult;
import com.example.baresse.moviespop.themoviedb.model.ReviewsResult;
import com.example.baresse.moviespop.themoviedb.model.TrailersResult;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TheMoviesDbHelper {

    private final String LOG_TAG = TheMoviesDbHelper.class.getSimpleName();

    private TheMoviesDbService service;

    private Context mContext;

    public TheMoviesDbHelper(Context context) {

        mContext = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TheMoviesDbService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(TheMoviesDbService.class);
    }

    public List<Movie> getMovies() {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        String favorites = mContext.getString(R.string.pref_order_favorites);
        String mostPopular = mContext.getString(R.string.pref_order_most_popular);
        String order = sharedPref.getString(mContext.getString(R.string.pref_order_key), mostPopular);

        if (favorites.equals(order)) {
            Log.d(LOG_TAG, "Fetch Movies ordered by favorites");
            return Favorites.getMovies();
        } else if (mostPopular.equals(order)) {
            Log.d(LOG_TAG, "Fetch Movies ordered by popularity");
            return executeCall(service.getMoviesOrderedByPopularity(BuildConfig.THE_MOVIES_DB_API_KEY));
        } else {
            Log.d(LOG_TAG, "Fetch Movies ordered by rating");
            return executeCall(service.getMoviesOrderedByRating(BuildConfig.THE_MOVIES_DB_API_KEY));
        }
    }

    private List<Movie> executeCall(Call<MoviesResult> moviesCall) {
        try {
            MoviesResult result = moviesCall.execute().body();
            return result.getResults();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            e.printStackTrace();
            return null;
        }
    }

    public MovieDetail getMovie (long id) {

        Call<MovieDetail> movieDetailCall =
                service.getMovieDetail(id, BuildConfig.THE_MOVIES_DB_API_KEY);

        Call<TrailersResult> trailersResultCall =
                service.getMovieTrailers(id, BuildConfig.THE_MOVIES_DB_API_KEY);

        Call<ReviewsResult> reviewsResultCall =
                service.getMovieReviews(id, BuildConfig.THE_MOVIES_DB_API_KEY);

        MovieDetail foundMovie;
        try {
            foundMovie = movieDetailCall.execute().body();

            // Fetch trailers
            foundMovie.setTrailers(trailersResultCall.execute().body().getResults());

            // Fetch reviews
            foundMovie.setReviews(reviewsResultCall.execute().body().getResults());

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            e.printStackTrace();
            return null;
        }

        return foundMovie;
    }
}
