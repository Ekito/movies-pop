package com.example.baresse.moviespop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baresse.moviespop.data.Cache;
import com.example.baresse.moviespop.themoviedb.model.Movie;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailMovieActivityFragment extends Fragment {

    private TextView mTitle;
    private ImageView mPosterView;
    private TextView mDateView;
    private TextView mRatingView;
    private TextView mSynopsisView;

    public DetailMovieActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail_movie, container, false);

        mTitle = (TextView) rootView.findViewById(R.id.title_textView);
        mPosterView = (ImageView) rootView.findViewById(R.id.poster_imageView);
        mDateView = (TextView) rootView.findViewById(R.id.releaseDate_textView);
        mRatingView = (TextView) rootView.findViewById(R.id.rating_textView);
        mSynopsisView = (TextView) rootView.findViewById(R.id.synopsis_textView);

        return rootView;
    }


    public void setMovie(long movieId) {
        Movie movie = Cache.getMovieById(movieId);

        Picasso.with(getContext()).load(movie.getPosterUrl()).into(mPosterView);
        mTitle.setText(movie.getTitle());
        mSynopsisView.setText(movie.getOverview());
        mDateView.setText(movie.getReleaseDate());
        mRatingView.setText("" + movie.getVoteAverage());
    }
}
