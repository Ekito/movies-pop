package com.example.baresse.moviespop.themoviedb;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.baresse.moviespop.BuildConfig;
import com.example.baresse.moviespop.R;
import com.example.baresse.moviespop.themoviedb.model.Movie;
import com.example.baresse.moviespop.themoviedb.model.MoviesResult;

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
        String mostPopular = mContext.getString(R.string.pref_order_most_popular);
        String order = sharedPref.getString(mContext.getString(R.string.pref_order_key), mostPopular);

        Call<MoviesResult> moviesCall;

        if (mostPopular.equals(order)) {
            Log.d(LOG_TAG, "Fetch Movies ordered by popularity ");
            moviesCall = service.getMoviesOrderedByPopularity(BuildConfig.THE_MOVIES_DB_API_KEY);
        } else {
            Log.d(LOG_TAG, "Fetch Movies ordered by rating ");
            moviesCall = service.getMoviesOrderedByRating(BuildConfig.THE_MOVIES_DB_API_KEY);
        }

        try {
            MoviesResult result = moviesCall.execute().body();
            return result.getResults();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            e.printStackTrace();
            return null;
        }
    }
}
