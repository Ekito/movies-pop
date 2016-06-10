package com.example.baresse.moviespop.activities.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.baresse.moviespop.R;
import com.example.baresse.moviespop.activities.detail.adapter.ReviewAdapter;
import com.example.baresse.moviespop.activities.detail.adapter.TrailerAdapter;
import com.example.baresse.moviespop.data.Favorites;
import com.example.baresse.moviespop.data.PicassoCache;
import com.example.baresse.moviespop.tasks.FetchMovieDetailsTask;
import com.example.baresse.moviespop.themoviedb.model.MovieDetail;
import com.example.baresse.moviespop.themoviedb.model.Trailer;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;

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
    private TextView mSynopsisView;

    private ExpandableHeightListView mReviewslistView;
    private ExpandableHeightListView mTrailerslistView;

    private ToggleButton mFavoriteToggleButton;
    private IconDrawable noFav;
    private IconDrawable fav;

    private ReviewAdapter mReviewAdapter;
    private TrailerAdapter mTrailerAdapter;
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
        mSynopsisView = (TextView) rootView.findViewById(R.id.synopsis_textView);

        mReviewslistView = (ExpandableHeightListView) rootView.findViewById(R.id.reviews_listView);
        mReviewAdapter = new ReviewAdapter(getContext(), R.layout.list_item_review);
        mReviewslistView.setAdapter(mReviewAdapter);
        mReviewslistView.setExpanded(true);

        mTrailerslistView = (ExpandableHeightListView) rootView.findViewById(R.id.trailers_listView);
        mTrailerAdapter = new TrailerAdapter(getContext(), R.layout.list_item_trailer);
        mTrailerslistView.setAdapter(mTrailerAdapter);
        mTrailerslistView.setExpanded(true);
        mTrailerslistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Trailer trailer = mTrailerAdapter.getItem(position);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailer.getUrl()));
                startActivity(intent);
            }
        });
        noFav = new IconDrawable(getContext(), MaterialIcons.md_favorite_border)
                .colorRes(R.color.colorAccent)
                .actionBarSize();

        fav = new IconDrawable(getContext(), MaterialIcons.md_favorite)
                .colorRes(R.color.colorAccent)
                .actionBarSize();

        mFavoriteToggleButton = (ToggleButton) rootView.findViewById(R.id.toggleButton);
        mFavoriteToggleButton.setBackgroundDrawable(noFav);
        mFavoriteToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setfavoritetoggleUI(isChecked);
                updateFavoritesCache(isChecked);
            }
        });

        updateUI();

        return rootView;
    }

    private void updateFavoritesCache(boolean isFavorite) {
        if (isFavorite) {
            Favorites.addMovie(mMovie);
        }
        else {
            Favorites.removeMovie(mMovie.getId());
        }
        Favorites.saveFavoritesMovies(getActivity().getBaseContext());
    }

    private void setfavoritetoggleUI(boolean isFavorite) {
        mFavoriteToggleButton.setChecked(isFavorite);
        if (isFavorite) {
            mFavoriteToggleButton.setBackgroundDrawable(fav);
        }
        else {
            mFavoriteToggleButton.setBackgroundDrawable(noFav);
        }
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

            // Update ReviewAdpater
            mReviewAdapter.clear();
            if (mMovie.getReviews() != null) {
                mReviewAdapter.addAll(mMovie.getReviews());
            }

            // Update TrailerAdpater
            mTrailerAdapter.clear();
            if (mMovie.getTrailers() != null) {
                mTrailerAdapter.addAll(mMovie.getTrailers());
            }

            mReviewslistView.setExpanded(true);
            mTrailerslistView.setExpanded(true);
        }
    }

    public void updateUI() {
        if (mMovie != null) {
            boolean isFavorite = Favorites.getMovieById(mMovie.getId()) != null;
            setfavoritetoggleUI(isFavorite);
            mTitle.setText(mMovie.getTitle());
            mSynopsisView.setText(mMovie.getOverview());
            mDateView.setText(formatDate(mMovie.getReleaseDate()));
            mRuntimeView.setText(formatRuntime(mMovie.getRuntime()));
            mRatingView.setText(formatRating(mMovie.getVoteAverage()));
            PicassoCache.getPicassoInstance(getContext()).load(mMovie.getPosterUrl()).into(mPosterView);
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
