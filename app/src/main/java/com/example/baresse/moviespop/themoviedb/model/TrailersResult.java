package com.example.baresse.moviespop.themoviedb.model;

import java.util.List;

public class TrailersResult {

    private long id;

    private List<Trailer> results;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }
}
