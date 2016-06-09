package com.example.baresse.moviespop.themoviedb;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TheMoviesDbHelper {

    private final String LOG_TAG = TheMoviesDbHelper.class.getSimpleName();

    private static final long TEN_MB = 10 * 1024 * 1024;
    private static final long SIX_HOURS = 60 * 60 * 6;
    private static final long SEVEN_DAYS = 60 * 60 * 24 * 7;

    private TheMoviesDbService service;

    private Context mContext;

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public TheMoviesDbHelper(Context context) {

        mContext = context;

        OkHttpClient client = new OkHttpClient
                .Builder()
                .cache(new Cache(mContext.getCacheDir(), TEN_MB)) // 10 MB
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if (isOnline()) {
                            request = request.newBuilder().header("Cache-Control", "public, max-age=" + SIX_HOURS).build();
                        } else {
                            request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + SEVEN_DAYS).build();
                        }
                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
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
            if (result != null) {
                return result.getResults();
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            e.printStackTrace();
            return null;
        }
    }

    public MovieDetail getMovie(long id) {

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
            TrailersResult trailersResult = trailersResultCall.execute().body();
            if (trailersResult != null) {
                foundMovie.setTrailers(trailersResult.getResults());
            }

            // Fetch reviews
            ReviewsResult reviewsResult = reviewsResultCall.execute().body();
            if (reviewsResult != null) {
                foundMovie.setReviews(reviewsResult.getResults());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            e.printStackTrace();
            return null;
        }

        return foundMovie;
    }
}
