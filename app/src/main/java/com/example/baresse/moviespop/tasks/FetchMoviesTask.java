package com.example.baresse.moviespop.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.baresse.moviespop.activities.main.adapter.MoviesGridViewAdapter;
import com.example.baresse.moviespop.themoviedb.TheMoviesDbHelper;
import com.example.baresse.moviespop.themoviedb.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class FetchMoviesTask extends AsyncTask<Void, Void, Movie[]> {

    private final Context mContext;
    private final MoviesGridViewAdapter mAdapter;

    public FetchMoviesTask(Context context, MoviesGridViewAdapter adapter) {
        mContext = context;
        mAdapter = adapter;
    }

    @Override
    protected Movie[] doInBackground(Void... params) {
        TheMoviesDbHelper helper = new TheMoviesDbHelper(mContext);

        List<Movie> movies = helper.getMovies();
        if (movies == null) {
            movies = new ArrayList<>();
        }

        return movies.toArray(new Movie[0]);
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(Movie[] result) {
        mAdapter.setMovies(result);
    }
}
