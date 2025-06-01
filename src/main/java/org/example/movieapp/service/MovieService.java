package org.example.movieapp.service;

import org.example.movieapp.model.Movie;
import org.example.movieapp.repo.MovieRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class MovieService {
    private final MovieRepository repo;

    /**
     * Constructor: repository here.
     */
    public MovieService(MovieRepository repo){
        if (repo == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.repo = repo;
    }

    /*
     * Adds a movie, checks for null and duplicates
     */
    public void addMovie(Movie movie){
        if(movie == null){
            throw new IllegalArgumentException("Movie Can't be null");
        }
        Movie duplicate = repo.findByTitle(movie.getTitle());
        if(duplicate != null){
            throw new IllegalArgumentException("" +
                    "A movie with title '" + movie.getTitle() + "' already exists");
        }
        repo.add(movie);
    }

    /**
     * Returns all movies in insertion order.
     */
    public List<Movie> listAllMovies() {
        return repo.findAll();
    }

    /**
     * Retrieves a movie by its UUID.
     *
     * @throws NoSuchElementException if no movie has that ID
     */
    public Movie getMovieById(UUID id) {
        Movie found = repo.findById(id);
        if (found == null) {
            throw new NoSuchElementException("Movie with ID " + id + " not found");
        }
        return found;
    }

    /**
     * Finds a movie by its title.
     *
     * @throws NoSuchElementException if not found
     */
    public Movie getMovieByTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null");
        }
        Movie found = repo.findByTitle(title);
        if (found == null) {
            throw new NoSuchElementException("Movie with title '" + title + "' not found");
        }
        return found;
    }

    /**
     * Adds a comment to an existing movie (identified by UUID).
     *
     * @throws NoSuchElementException    if the movie does not exist
     * @throws IllegalArgumentException if the comment is null/blank
     */
    public void addComment(UUID movieId, String comment) {
        Movie target = repo.findById(movieId);
        if (target == null) {
            throw new NoSuchElementException("Cannot add comment: movie ID " + movieId + " not found");
        }
        target.addComment(comment);
    }

    public void deleteMovie(UUID movieId){
        Movie target = repo.findById(movieId);
        if (target == null) {
            throw new NoSuchElementException("Movie ID " + movieId + " not found");
        }
        repo.delete(movieId);
    }
}
