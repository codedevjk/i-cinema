package com.icinema.service;

import com.icinema.dto.MovieDTO;
import java.util.List;

public interface MovieService {
    List<MovieDTO> getAllMovies();

    List<MovieDTO> searchMovies(String title);

    MovieDTO getMovieById(Long id);

    // US 10: Update rating
    MovieDTO rateMovie(Long movieId, Long userId, Integer rating);

    Integer getUserRating(Long movieId, Long userId);
}
