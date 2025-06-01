# MovieApp

A simple Java-based movie application demonstrating clean code principles, in-memory storage, and automated testing with JUnit 5.

---

## 1. Code & Test Documentation

- **Javadoc and inline comments** are used throughout for clarity.
  - In `Movie.java`, each setter includes validation checks (e.g., non-blank title, valid year).
  - In `MovieRepository.java`, methods have comments explaining their behavior (e.g., why we return a new List in `findAll()`).
  - In `MovieService.java`, every public method has Javadoc explaining its purpose and exceptions.
- **Descriptive test names** in JUnit 5 (`@DisplayName`) explain what each test verifies.
  - Example from `MovieRepositoryTest.java`:
    ```java
    @Test
    @DisplayName("findByTitle() should locate a movie by exact title")
    void findByTitle() { ... }
    ```
  - Example from `MovieServiceTest.java`:
    ```java
    @Test
    @DisplayName("deleteMovie() removes existing or throws if missing")
    void deleteMovieSuccessAndFail() { ... }
    ```

---

## 2. Clean Code Practices

Here are three examples of how the code follows clean-code principles.

1. **Defensive Copying & Unmodifiable Lists**  
   Prevents external code from modifying internal collections directly.  
   ```java
   public List<String> getGenres() {
       return Collections.unmodifiableList(genres);
   }
    ```

2. **Validation in Setters**
   Ensures class invariants (e.g., valid release year) are maintained:

   ```java
   public void setReleaseYear(int releaseYear) {
       int currentYear = Year.now().getValue();
       if (releaseYear < 1888 || releaseYear > currentYear) {
           throw new IllegalArgumentException(
               "Release year must be between 1888 and " + currentYear);
       }
       this.releaseYear = releaseYear;
   }
   ```

3. **Guard Clauses & Clear Exception Messages**
   “Fail fast” when assumptions are violated, with helpful messages:

   ```java
   public void addMovie(Movie movie) {
       if (movie == null) {
           throw new IllegalArgumentException("Cannot add a null movie");
       }
       Movie existing = repo.findByTitle(movie.getTitle());
       if (existing != null) {
           throw new IllegalArgumentException(
               "A movie with title '" + movie.getTitle() + "' already exists");
       }
       repo.add(movie);
   }
   ```

---

## 3. Project Overview

* **What it does**

    * Stores movies in memory (title, director, year, genres, rating, duration, comments).
    * Allows lookup by UUID or title, adding comments, and deleting movies.

* **How it works**

    1. **Model**: `Movie.java` holds data with validation in setters.
    2. **Repository**: `MovieRepository.java` uses an `ArrayList<Movie>` to add, find, list, and delete.
    3. **Service**: `MovieService.java` enforces business rules (e.g., no duplicate titles) and delegates to the repository.
    4. **Tests**: JUnit 5 tests cover both repository and service layers.

* **Test Cases**

    * **Repository tests** (`MovieRepositoryTest.java`):

        * `addAndFindById()`: verify a saved movie can be fetched by UUID.
        * `findByTitleNotFoundReturnsNull()`: searching a missing title returns `null`.
        * `deleteByIdRemovesMovie()`: confirms deletion logic.
    * **Service tests** (`MovieServiceTest.java`):

        * `addMovieSuccessAndDuplicateThrows()`: ensures duplicates are rejected.
        * `getMovieByIdOrFail()`: checks retrieval and exception if not found.
        * `addCommentSuccessAndFail()`: verifies adding comments and exception on invalid UUID.
        * `deleteMovieSuccessAndFail()`: confirms deletion and exception for non-existent ID.

---

## 4. Dependencies

All dependencies are managed via Maven and fetched from Maven Central:

* **Java 23 (Temurin)**

    * Configured in GitHub Actions with `actions/setup-java@v4` (distribution: `temurin`, version: `23`).

* **JUnit Jupiter (5.13.0-RC1)**

  ```xml
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.13.0-RC1</version>
    <scope>test</scope>
  </dependency>
  ```

* **Maven Surefire Plugin** (runs tests during build)

  ```xml
  <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.0.0-M7</version>
  </plugin>
  ```

* **GitHub Actions**

    * `actions/checkout@v4` (checks out code).
    * `actions/setup-java@v4` (installs Temurin JDK 23 and caches Maven).

---

### How to run locally

```bash
# In project root:
mvn test     # compile and run all tests
mvn package  # compile, test, and build the JAR
```

---


