package com.example.baresse.moviespop.activities.main;

import android.content.Context;
import android.widget.AbsListView;

import com.squareup.picasso.Picasso;

public class MoviesScrollListener implements AbsListView.OnScrollListener {

    private final Context mContext;

    public MoviesScrollListener(Context context) {
        mContext = context;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        final Picasso picasso = Picasso.with(mContext);
        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
            picasso.resumeTag(mContext);
        } else {
            picasso.pauseTag(mContext);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // Do nothing
    }
}
