package com.example.baresse.moviespop;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bluejamesbond.text.DocumentView;
import com.example.baresse.moviespop.adapter.ReviewAdapter;
import com.example.baresse.moviespop.tasks.FetchMovieDetailsTask;
import com.example.baresse.moviespop.themoviedb.model.MovieDetail;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailMovieActivityFragment extends Fragment {

    private final String LOG_TAG = DetailMovieActivityFragment.class.getSimpleName();

    private MovieDetail mMovie;
    private TextView mTitle;
    private ImageView mPosterView;
    private TextView mDateView;
    private TextView mRuntimeView;
    private TextView mRatingView;
    private DocumentView mSynopsisView;

    private ToggleButton mFavoriteToggleButton;
    private IconDrawable noFav;
    private IconDrawable fav;

    private ReviewAdapter mReviewAdapter;
    private Context mContext;

    public DetailMovieActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = getContext();

        View rootView = inflater.inflate(R.layout.fragment_detail_movie, container, false);

        mTitle = (TextView) rootView.findViewById(R.id.title_textView);
        mPosterView = (ImageView) rootView.findViewById(R.id.poster_imageView);
        mDateView = (TextView) rootView.findViewById(R.id.releaseDate_textView);
        mRuntimeView = (TextView) rootView.findViewById(R.id.runtime_textView);
        mRatingView = (TextView) rootView.findViewById(R.id.rating_textView);
        mSynopsisView = (DocumentView) rootView.findViewById(R.id.synopsis_textView);

        ExpandableHeightListView mReviewslistView = (ExpandableHeightListView) rootView.findViewById(R.id.reviews_listView);
        mReviewAdapter = new ReviewAdapter(getContext(), R.layout.list_item_review);
        mReviewslistView.setAdapter(mReviewAdapter);
        mReviewslistView.setExpanded(true);

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

    public void fetchMovie(long movieId) {

        // Fetch movies from the network
        Log.d(LOG_TAG, "Fetch Movie details...");
        new FetchMovieDetailsTask(getContext(), this).execute(movieId);
    }

    public void setMovie(MovieDetail movie) {
        if (movie != null) {
            mMovie = movie;
            updateUI();
            mReviewAdapter.clear();
            if (mMovie.getReviews() != null) {
                mReviewAdapter.addAll(mMovie.getReviews());
            }
        }
    }

    public void updateUI() {
        if (mMovie != null) {
            Picasso.with(getContext()).load(mMovie.getPosterUrl()).into(mPosterView);
            mTitle.setText(mMovie.getTitle());
            mSynopsisView.setText(mMovie.getOverview());
            mDateView.setText(formatDate(mMovie.getReleaseDate()));
            mRuntimeView.setText(formatRuntime(mMovie.getRuntime()));
            mRatingView.setText(formatRating(mMovie.getVoteAverage()));
        }
    }

    private String formatRuntime(int runtime) {
        if (mContext != null) {
            return String.format(mContext.getString(R.string.format_runtime), runtime);
        } else {
            return "";
        }
    }

    private String formatRating(float voteAvg) {
        if (mContext != null) {
            return String.format(mContext.getString(R.string.format_rating), voteAvg);
        } else {
            return "";
        }
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
