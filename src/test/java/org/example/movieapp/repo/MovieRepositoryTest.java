package org.example.movieapp.repo;

import org.example.movieapp.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.UUID;

public class MovieRepositoryTest {
    private MovieRepository repo;
    private Movie inception;
    private Movie matrix;

    @BeforeEach
    void setUp() {
        repo = new MovieRepository();

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
    void findAndAddById(){
        repo.add(matrix);
        Movie found = repo.findById(matrix.getId());
        Assertions.assertNotNull(found);
        Assertions.assertEquals("The Matrix", found.getTitle());
    }

    @Test
    void findByIdNotFoundReturnsNull() {
        Movie notFound = repo.findById(UUID.randomUUID());
        Assertions.assertNull(notFound);
    }

    @Test
    void findByTitle() {
        repo.add(inception);
        repo.add(matrix);

        Movie found = repo.findByTitle("The Matrix");
        Assertions.assertNotNull(found);
        Assertions.assertEquals(matrix.getId(), found.getId());
    }

    @Test
    void findAllReturnsListInOrder() {
        repo.add(inception);
        repo.add(matrix);

        List<Movie> all = repo.findAll();
        Assertions.assertEquals(2, all.size());
        Assertions.assertEquals("Inception", all.get(0).getTitle());
        Assertions.assertEquals("The Matrix", all.get(1).getTitle());
    }

    @Test
    void deleteByIdRemovesMovie() {
        repo.add(inception);
        UUID id = inception.getId();

        boolean removed = repo.delete(id);
        Assertions.assertTrue(removed);
        Assertions.assertNull(repo.findById(id));
    }

    @Test
    void deleteByIdNotFoundReturnsFalse() {
        boolean removed = repo.delete(UUID.randomUUID());
        Assertions.assertFalse(removed);
    }

    @Test
    void addNullThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> repo.add(null));
    }

}
