package com.example.baresse.moviespop.activities.main;


import android.content.Context;
import android.util.Log;

import com.example.baresse.moviespop.activities.main.adapter.MoviesGridViewAdapter;
import com.example.baresse.moviespop.tasks.FetchMoviesTask;
import com.example.baresse.moviespop.themoviedb.model.Movie;

import java.util.concurrent.ExecutionException;

public class MoviesScrollListener extends EndlessScrollListener {

    private final String LOG_TAG = MoviesScrollListener.class.getSimpleName();

    private final Context mContext;
    private final MoviesGridViewAdapter mAdapter;


    public MoviesScrollListener(Context context, MoviesGridViewAdapter adapter) {
        mContext = context;
        mAdapter = adapter;
    }

    @Override
    public boolean onLoadMore(int page, int totalItemsCount) {
        try {
            Movie[] movies = new FetchMoviesTask(mContext, page).execute().get();
            mAdapter.addMovies(movies);
            return true;
        } catch (InterruptedException | ExecutionException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            return false;
        }
    }
}