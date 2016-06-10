package com.example.baresse.moviespop.activities.main.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.baresse.moviespop.R;
import com.example.baresse.moviespop.network.PicassoCache;
import com.example.baresse.moviespop.themoviedb.model.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

public class MoviesGridViewAdapter extends BaseAdapter {

    private final String LOG_TAG = MoviesGridViewAdapter.class.getSimpleName();
    private final Context mContext;

    private final List<Movie> movies = new ArrayList<>();

    public MoviesGridViewAdapter(Context context) {
        mContext = context;
    }

    public void setMovies(Movie[] foundMovies) {
        movies.clear();
        addMovies(foundMovies);
    }

    public void addMovies(Movie[] foundMovies) {
        Collections.addAll(movies, foundMovies);
        Log.d(LOG_TAG, movies.toString());

        this.notifyDataSetChanged();
    }

    public Movie getMovie(int position) {
        return movies.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view = (ImageView) convertView;
        if (view == null) {
            view = new ImageView(mContext);
            view.setScaleType(CENTER_CROP);
        }

        // Get the image URL for the current position.
        String url = getItem(position);

        PicassoCache.getPicassoInstance(mContext).setIndicatorsEnabled(true);

        // Trigger the download of the URL asynchronously into the image view.
        PicassoCache.getPicassoInstance(mContext)
                .load(url) //
                .placeholder(R.drawable.placeholder) //
                .error(R.drawable.error) //
                .fit() //
                .tag(mContext) //
                .into(view);

        return view;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public String getItem(int position) {
        return movies.get(position).getPosterUrl();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
