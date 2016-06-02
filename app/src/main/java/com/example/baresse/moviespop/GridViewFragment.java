package com.example.baresse.moviespop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * A placeholder fragment containing a grid view.
 */
public class GridViewFragment extends Fragment {

    public GridViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gridview_activity, container, false);

        GridView gv = (GridView) rootView.findViewById(R.id.grid_view);
        if (gv != null) {
            MoviesGridViewAdapter adapter = new MoviesGridViewAdapter(getContext());

            gv.setAdapter(adapter);
            gv.setOnScrollListener(new MoviesScrollListener(getContext()));
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    //adapter.getItem(position).launch(PicassoSampleActivity.this);
                }
            });
        }

        return rootView;
    }
}
