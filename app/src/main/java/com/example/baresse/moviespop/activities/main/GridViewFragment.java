package com.example.baresse.moviespop.activities.main;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.baresse.moviespop.tasks.FetchMoviesTask;
import com.example.baresse.moviespop.themoviedb.model.Movie;

import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a grid view.
 */
public class GridViewFragment extends Fragment {

    private final String LOG_TAG = GridViewFragment.class.getSimpleName();

    private GridView gridview;
    private MoviesGridViewAdapter mAdapter;

    public GridViewFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.gridview_activity, container, false);

        gridview = (GridView) rootView.findViewById(R.id.grid_view);
        mAdapter = new MoviesGridViewAdapter(getContext());
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

        // Fetch the first page of document
        try {
            Movie[] movies = new FetchMoviesTask(getContext(), 0).execute().get();
            mAdapter.setMovies(movies);
        } catch (InterruptedException | ExecutionException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        return rootView;
    }
}
