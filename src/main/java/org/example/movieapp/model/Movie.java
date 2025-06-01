package org.example.movieapp.model;

import java.time.Year;
import java.util.*;

public class Movie {
    private final UUID id;
    private String title;
    private String director;
    private int releaseYear;
    private int duration; // in minutes
    private List<String> genres;
    private double rating;
    private List<String> comments = new ArrayList<>();

    public Movie(String title, String director, int releaseYear, int duration, List<String> genres, double rating) {
        this.id = UUID.randomUUID();
        setTitle(title);
        setDirector(director);
        setReleaseYear(releaseYear);
        setDuration(duration);
        setGenres(genres);
        setRating(rating);
    }

    public String getTitle() {
        return title;
    }

    public UUID getId() {
        return id;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be blank");
        }
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        if (director == null || director.isBlank()) {
            throw new IllegalArgumentException("Director cannot be blank");
        }
        this.director = director;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        int currentYear = Year.now().getValue();
        if (releaseYear < 1888 || releaseYear > currentYear) {
            throw new IllegalArgumentException("Release year must be between 1888 and the current year");
        }
        this.releaseYear = releaseYear;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be a positive integer");
        }
        this.duration = duration;
    }

    /** Returns an unmodifiable list to protect internal state */
    public List<String> getGenres() {
        return Collections.unmodifiableList(genres);
    }

    public void setGenres(List<String> genres) {
        if (genres == null || genres.isEmpty()) {
            throw new IllegalArgumentException("Genres cannot be null or empty");
        }
        this.genres = genres;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }
        this.rating = rating;
    }

    public List<String> getComments() {
        return Collections.unmodifiableList(comments);
    }

    // ===== Convenience Methods =====
    public void addGenre(String genre) {
        if( genre == null || genre.isBlank()) {
            throw new IllegalArgumentException("Genre cannot be blank");
        }
        genres.add(genre);
    }
    public void addComment(String comment) {
        if (comment == null || comment.isBlank()) {
            throw new IllegalArgumentException("Comment cannot be empty");
        }
        comments.add(comment.trim());
    }
    public void removeComment(String comment) {
        comments.remove(comment);
    }

    // ===== Utility Overrides =====
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie m)) return false;
        return Objects.equals(id, m.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public String toString() {
        return String.format("Movie[%s (%d) by %s, rating=%.1f]",
                title, releaseYear, director, rating);
    }

}

