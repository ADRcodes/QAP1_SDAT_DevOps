package org.example.movieapp.service;

import org.example.movieapp.model.Movie;
import org.example.movieapp.repo.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/*
* Tests:
* - Add movie works
* - Add movie with same title fails
* - Get movie by id works when passed correct id
* - Get movie by id fails when passed incorrect id
* - Get movie by title works when passed correct title
* - Get movie by title fails when passed incorrect title
* - Add comment works when passed valid ID and valid comment
* - Add comment fails when passed invalid ID and valid comment
* - Add comment fails when passed valid ID and invalid comment
* - Delete movie works
*
* */

public class MovieServiceTest {
    private MovieRepository repo;
    private MovieService service;
    private Movie inception;
    private Movie matrix;

    @BeforeEach
    void setup(){
        repo = new MovieRepository();
        service = new MovieService(repo);

        inception = new Movie(
                "Inception",
                "Christopher Nolan",
                2010,
                148,
                List.of("Sci-Fi", "Thriller"),
                8.8
        );

        matrix = new Movie(
                "The Matrix",
                "The Wachowskis",
                1999,
                136,
                List.of("Sci-Fi", "Action"),
                8.7
        );
    }

    @Test
    @DisplayName("addMovie() stores a new movie, then listAllMovies() returns it")
    void addAndListAll() {
        service.addMovie(inception);
        List<Movie> all = service.listAllMovies();
        Assertions.assertEquals(1, all.size());
        Assertions.assertEquals("Inception", all.get(0).getTitle());
    }

    @Test
    @DisplayName("addMovie() with duplicate title throws IllegalArgumentException")
    void addDuplicateTitleThrows() {
        service.addMovie(inception);
        Movie dup = new Movie(
                "Inception",
                "Rizlopher Brolan",
                1930,
                1048,
                List.of("Sci-Fry", "Thriller"),
                8.8
        );
        Exception ex = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.addMovie(dup)
        );
        Assertions.assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    @DisplayName("getMovieById() returns the correct movie or throws if missing")
    void getMovieByIdOrFail() {
        service.addMovie(matrix);
        UUID id = matrix.getId();

        Movie found = service.getMovieById(id);
        Assertions.assertEquals("The Matrix", found.getTitle());

        UUID fakeId = UUID.randomUUID();
        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> service.getMovieById(fakeId)
        );
    }

    @Test
    @DisplayName("getMovieByTitle() returns by title or throws if missing")
    void getMovieByTitleOrFail() {
        service.addMovie(inception);

        Movie found = service.getMovieByTitle("Inception");
        Assertions.assertNotNull(found);
        Assertions.assertEquals(inception.getId(), found.getId());

        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> service.getMovieByTitle("Avatar")
        );
    }

    @Test
    @DisplayName("addComment() appends a valid comment or throws if movie missing")
    void addCommentSuccessAndFail() {
        service.addMovie(inception);
        UUID id = inception.getId();

        service.addComment(id, "Mindbendingly decent!");
        Assertions.assertEquals(1, service.getMovieById(id).getComments().size());

        UUID fakeId = UUID.randomUUID();
        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> service.addComment(fakeId, "This won’t work")
        );
    }

    @Test
    @DisplayName("deleteMovie() removes existing or throws if missing")
    void deleteMovieSuccessAndFail() {
        service.addMovie(matrix);
        UUID id = matrix.getId();

        service.deleteMovie(id);
        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> service.getMovieById(id)
        );

        UUID fakeId = UUID.randomUUID();
        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> service.deleteMovie(fakeId)
        );
    }

    @Test
    @DisplayName("Constructor throws if repository is null")
    void constructorNullRepo() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new MovieService(null)
        );
    }


}
