package org.example.movieapp.repo;

import org.example.movieapp.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MovieRepository {
    private final List<Movie> store = new ArrayList<>();

    /**
     * Adds a new movie to the repository.
     */
    public void add(Movie movie){
        if (movie == null) {
            throw new IllegalArgumentException("Cannot add a null movie");
        }
        store.add(movie);
    }

    /**
     * Finds a movie by its UUID.
     * @return the Movie if found, or null if no movie has that ID.
     */
    public Movie findById(UUID id){
        if (id == null) return null;
        for(Movie m : store){
            if (m.getId().equals(id)){
                return m;
            }
        }
        return null;
    }

    /**
     * Finds a movie by its exact title (case-sensitive).
     * @return the first Movie with this title, or null if not found.
     */
    public Movie findByTitle(String title) {
        if (title == null) return null;
        for (Movie m : store) {
            if (m.getTitle().equals(title)) {
                return m;
            }
        }
        return null;
    }

    public List<Movie> findAll(){
        return new ArrayList<>(store);
    }

    public boolean delete(UUID id){
        if (id == null) return false;
        for(int i = 0 ; i < store.size() ; i++){
            if (store.get(i).getId().equals(id)){
                store.remove(i);
                return true;
            }
        }
        return false;
    }
}
