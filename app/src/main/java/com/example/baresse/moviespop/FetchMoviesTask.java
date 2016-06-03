package com.example.baresse.moviespop;

import android.content.Context;
import android.os.AsyncTask;

import com.example.baresse.moviespop.themoviedb.TheMoviesDbHelper;
import com.example.baresse.moviespop.themoviedb.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class FetchMoviesTask extends AsyncTask<Void, Void, String[]> {

    private final Context mContext;
    private final MoviesGridViewAdapter mAdapter;

    public FetchMoviesTask(Context context, MoviesGridViewAdapter adapter) {
        mContext = context;
        mAdapter = adapter;
    }

    @Override
    protected String[] doInBackground(Void... params) {
        TheMoviesDbHelper helper = new TheMoviesDbHelper(mContext);
        List<String> urls = new ArrayList<>();

        for (Movie movie : helper.getMovies()) {
            urls.add(movie.getPosterUrl());
        }

        return urls.toArray(new String[0]);
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String[] result) {
        mAdapter.setPosterUrls(result);
    }
}
