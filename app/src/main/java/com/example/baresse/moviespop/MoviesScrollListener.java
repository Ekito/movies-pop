package com.example.baresse.moviespop;

import android.content.Context;
import android.widget.AbsListView;

public class MoviesScrollListener implements AbsListView.OnScrollListener {

    private final Context mContext;

    public MoviesScrollListener(Context context) {
        mContext = context;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
