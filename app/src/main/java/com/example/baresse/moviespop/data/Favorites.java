package com.example.baresse.moviespop.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.baresse.moviespop.themoviedb.model.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Favorites {

    private final static String FAVORITES_MOVIES = "FAVORITES_MOVIES";

    private static Map<Long, Movie> favoritesMovies = new HashMap<>();

    public static void saveFavoritesMovies(Context ctx) {
        String json = JsonHelper.toJson(getMovies());
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        sharedPref.edit().putString(FAVORITES_MOVIES, json).apply();
    }

    public static void loadFavoritesMovies(Context ctx) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        String json = sharedPref.getString(FAVORITES_MOVIES, null);

        if (json != null) {
            Type listType = new TypeToken<ArrayList<Movie>>(){}.getType();
            List<Movie> movies = new Gson().fromJson(json, listType);
            setFavoritesMovies(movies);
        }
    }

    public static void setFavoritesMovies(List<Movie> movies) {

        favoritesMovies.clear();
        for (Movie m : movies) {
            favoritesMovies.put(m.getId(), m);
        }
    }

    public static void addMovie(Movie movie) {
        if (movie != null) {
            favoritesMovies.put(movie.getId(), movie);
        }
    }

    public static List<Movie> getMovies() {
        return new ArrayList<>(favoritesMovies.values());
    }

    public static Movie getMovieById(long id) {
        return favoritesMovies.get(id);
    }

    public static void removeMovie(long id) {
        favoritesMovies.remove(id);
    }
}
