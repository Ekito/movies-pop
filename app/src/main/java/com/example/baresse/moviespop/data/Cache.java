package com.example.baresse.moviespop.data;

import com.example.baresse.moviespop.themoviedb.model.Movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cache {

    private static Map<Long, Movie> cachedMovies = new HashMap<>();

    public static void setCachedMovies(List<Movie> movies) {

        cachedMovies.clear();
        for (Movie m : movies) {
            cachedMovies.put(m.getId(), m);
        }
    }

    public static List<Movie> getMovies() {
        return new ArrayList<>(cachedMovies.values());
    }

    public static Movie getMovieById(long id) {
        return cachedMovies.get(id);
    }
}
