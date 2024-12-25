package com.example.moviewatch;

public class Movie {
    private String name;
    private int imageResource;

    public Movie(String name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }
}
