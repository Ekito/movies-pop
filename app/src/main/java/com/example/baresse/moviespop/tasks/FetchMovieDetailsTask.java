package com.example.baresse.moviespop.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.baresse.moviespop.activities.detail.DetailMovieActivityFragment;
import com.example.baresse.moviespop.themoviedb.TheMoviesDbHelper;
import com.example.baresse.moviespop.themoviedb.model.MovieDetail;

public class FetchMovieDetailsTask extends AsyncTask<Long, Void, MovieDetail> {

    private final Context mContext;
    private final DetailMovieActivityFragment mFragment;

    public FetchMovieDetailsTask(Context context, DetailMovieActivityFragment fragment) {
        mContext = context;
        mFragment = fragment;
    }

    @Override
    protected MovieDetail doInBackground(Long... params) {
        TheMoviesDbHelper helper = new TheMoviesDbHelper(mContext);

        long movieId = params[0];
        return helper.getMovie(movieId);
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(MovieDetail movie) {
        mFragment.setMovie(movie);
    }
}
