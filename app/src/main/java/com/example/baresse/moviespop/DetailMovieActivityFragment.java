package com.example.baresse.moviespop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bluejamesbond.text.DocumentView;
import com.example.baresse.moviespop.data.Cache;
import com.example.baresse.moviespop.themoviedb.model.Movie;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailMovieActivityFragment extends Fragment {

    private Movie mMovie;
    private TextView mTitle;
    private ImageView mPosterView;
    private TextView mDateView;
    private TextView mRatingView;
    private DocumentView mSynopsisView;

    private ToggleButton mFavoriteToggleButton;
    private IconDrawable noFav;
    private IconDrawable fav;

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
        mSynopsisView = (DocumentView) rootView.findViewById(R.id.synopsis_textView);

        noFav = new IconDrawable(getContext(), MaterialIcons.md_favorite_border)
                .colorRes(R.color.colorAccent)
                .actionBarSize();

        fav = new IconDrawable(getContext(), MaterialIcons.md_favorite)
                .colorRes(R.color.colorAccent)
                .actionBarSize();

        mFavoriteToggleButton = (ToggleButton) rootView.findViewById(R.id.toggleButton);
        mFavoriteToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mFavoriteToggleButton.setBackgroundDrawable(fav);
                else
                    mFavoriteToggleButton.setBackgroundDrawable(noFav);
            }
        });

        //TODO: Retrieve state from the database...
        boolean isFavorite = false;
        setFavoriteToggle(isFavorite);

        updateUI();

        return rootView;
    }

    private void setFavoriteToggle(boolean isFavorite) {
        mFavoriteToggleButton.setChecked(isFavorite);
        if (isFavorite)
            mFavoriteToggleButton.setBackgroundDrawable(fav);
        else
            mFavoriteToggleButton.setBackgroundDrawable(noFav);
    }

    public void setMovie(long movieId) {
        mMovie = Cache.getMovieById(movieId);
        updateUI();
    }

    public void updateUI() {
        if (mMovie != null) {
            Picasso.with(getContext()).load(mMovie.getPosterUrl()).into(mPosterView);
            mTitle.setText(mMovie.getTitle());
            mSynopsisView.setText(mMovie.getOverview());
            mDateView.setText(formatDate(mMovie.getReleaseDate()));
            mRatingView.setText(formatRating(mMovie.getVoteAverage()));
        }
    }

    private String formatRating(float voteAvg) {
        return String.format(getContext().getString(R.string.format_rating), voteAvg);
    }

    /**
     * @param releaseDate string formatted like : 'YYYY-MM-DD'
     * @return Return only the year from the releaseDate string formatted like : 'YYYY-MM-DD'
     */
    private String formatDate(String releaseDate) {
        String[] parts = releaseDate.split("-");
        return parts[0];
    }
}
