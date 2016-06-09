package com.example.baresse.moviespop;

import android.app.Application;

import com.example.baresse.moviespop.data.Favorites;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialModule;


public class MoviePopApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Favorites.loadFavoritesMovies(getBaseContext());
        Iconify.with(new MaterialModule());
    }


}