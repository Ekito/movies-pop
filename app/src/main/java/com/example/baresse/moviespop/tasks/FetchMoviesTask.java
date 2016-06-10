package com.example.baresse.moviespop.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.baresse.moviespop.themoviedb.TheMoviesDbHelper;
import com.example.baresse.moviespop.themoviedb.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class FetchMoviesTask extends AsyncTask<Void, Void, Movie[]> {

    private final Context mContext;
    private final int mPageOffset;

    public FetchMoviesTask(Context context, int pageOffset) {
        mContext = context;
        mPageOffset = pageOffset;
    }

    @Override
    protected Movie[] doInBackground(Void... params) {
        TheMoviesDbHelper helper = new TheMoviesDbHelper(mContext);

        List<Movie> movies = helper.getMovies(mPageOffset);
        if (movies == null) {
            movies = new ArrayList<>();
        }

        return movies.toArray(new Movie[0]);
    }
}
