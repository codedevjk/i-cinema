package com.icinema.repository;

import com.icinema.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitleContainingIgnoreCase(String title);
    // Filters
    // Real query might need Specification for complex filtering (Rating, Genre,
    // Language together)
    // For simple US 01/02/03:
    // We can use simple finders or a custom query.
}
