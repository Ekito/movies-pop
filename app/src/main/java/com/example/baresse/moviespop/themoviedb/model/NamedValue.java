package com.example.baresse.moviespop.themoviedb.model;

public class NamedValue {

    private long id;

    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NamedValue{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
