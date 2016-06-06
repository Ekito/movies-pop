package com.example.baresse.moviespop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class DetailMovieActivity extends AppCompatActivity {

    private final String LOG_TAG = DetailMovieActivity.class.getSimpleName();

    public static String MOVIE_ID = "movie_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
           ab.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        long movieId = intent.getLongExtra(DetailMovieActivity.MOVIE_ID, -1);

        DetailMovieActivityFragment fragment = (DetailMovieActivityFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragmentDetail);
        fragment.setMovie(movieId);
    }

}
