package com.icinema.service;

import com.icinema.dto.MovieDTO;
import java.util.List;

public interface MovieService {
    List<MovieDTO> getAllMovies();

    List<MovieDTO> searchMovies(String title);

    MovieDTO getMovieById(Long id);

    // US 10: Update rating
    MovieDTO rateMovie(Long movieId, Integer rating);
}
