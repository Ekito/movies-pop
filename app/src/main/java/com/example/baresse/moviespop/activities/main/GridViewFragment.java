package com.example.baresse.moviespop.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.baresse.moviespop.R;
import com.example.baresse.moviespop.activities.detail.DetailMovieActivity;
import com.example.baresse.moviespop.activities.main.adapter.MoviesGridViewAdapter;
import com.example.baresse.moviespop.data.JsonHelper;
import com.example.baresse.moviespop.tasks.FetchMoviesTask;
import com.example.baresse.moviespop.themoviedb.model.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a grid view.
 */
public class GridViewFragment extends Fragment {

    public static final String MOVIES = "movies";

    private final String LOG_TAG = GridViewFragment.class.getSimpleName();

    private MoviesGridViewAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            String values = savedInstanceState.getString(MOVIES);

            if (values != null) {
                Type listType = new TypeToken<ArrayList<Movie>>() {
                }.getType();
                List<Movie> movies = new Gson().fromJson(values, listType);
                mAdapter = new MoviesGridViewAdapter(getContext());
                mAdapter.setMovies(movies.toArray(new Movie[movies.size()]));
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save movies for future reuse
        if (mAdapter.getMovies() != null) {
            String values = JsonHelper.toJson(mAdapter.getMovies());
            if (outState != null && values != null) {
                outState.putString(MOVIES, values);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gridview_activity, container, false);

        GridView gridview = (GridView) rootView.findViewById(R.id.grid_view);
        if (mAdapter == null) {
            Log.d(LOG_TAG, "nb will reset to 20 because mAdapter is null");
            mAdapter = new MoviesGridViewAdapter(getContext());

            // Fetch the first page of document
            try {
                Movie[] movies = new FetchMoviesTask(getContext(), 0).execute().get();
                mAdapter.setMovies(movies);
            } catch (InterruptedException | ExecutionException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
            }
        }

        gridview.setAdapter(mAdapter);
        gridview.setOnScrollListener(new MoviesScrollListener(getContext(), mAdapter));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailMovieActivity.class);

                Movie movie = mAdapter.getMovie(position);
                intent.putExtra(DetailMovieActivity.MOVIE_ID, movie.getId());
                startActivity(intent);
            }
        });

        return rootView;
    }
}
