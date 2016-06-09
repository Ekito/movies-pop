package com.example.baresse.moviespop.data;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class JsonHelper {
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZ";

    static SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    private static Gson gson = new Gson();

    public static SimpleDateFormat getSimpleDateFormat() {
        return dateFormat;
    }

    public static String toJson(Object o) {
        return gson.toJson(o);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}