package com.example.baresse.moviespop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * A placeholder fragment containing a grid view.
 */
public class GridViewFragment extends Fragment {

    private final String LOG_TAG = GridViewFragment.class.getSimpleName();

    private MoviesGridViewAdapter mAdapter;

    public GridViewFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    private void updateMovies() {
        // Fetch movies from the network
        Log.d(LOG_TAG, "Update Movies...");
        new FetchMoviesTask(getContext(), mAdapter).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gridview_activity, container, false);

        GridView gv = (GridView) rootView.findViewById(R.id.grid_view);
        mAdapter = new MoviesGridViewAdapter(getContext());
        gv.setAdapter(mAdapter);
        gv.setOnScrollListener(new MoviesScrollListener(getContext()));
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                startActivity(new Intent(getActivity(), DetailMovieActivity.class));
                //adapter.getItem(position).launch(PicassoSampleActivity.this);
            }
        });


        return rootView;
    }
}
