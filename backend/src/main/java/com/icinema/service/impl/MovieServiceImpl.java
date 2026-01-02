package com.icinema.service.impl;

import com.icinema.dto.MovieDTO;
import com.icinema.entity.Movie;
import com.icinema.exception.ResourceNotFoundException;
import com.icinema.repository.MovieRepository;
import com.icinema.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<MovieDTO> getAllMovies() {
        log.info("Fetching all movies");
        return movieRepository.findAll().stream()
                .map(movie -> modelMapper.map(movie, MovieDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> searchMovies(String title) {
        log.info("Searching movies with title: {}", title);
        return movieRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(movie -> modelMapper.map(movie, MovieDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MovieDTO getMovieById(Long id) {
        log.info("Fetching movie with id: {}", id);
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
        return modelMapper.map(movie, MovieDTO.class);
    }

    @Override
    public MovieDTO rateMovie(Long movieId, Integer rating) {
        log.info("Rating movie id: {} with {} stars", movieId, rating);
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        // Update average rating logic
        // Current Average = (OldAvg * OldCount + NewRating) / (OldCount + 1)
        double currentRating = movie.getRating() != null ? movie.getRating() : 0.0;
        int currentVotes = movie.getTotalVotes() != null ? movie.getTotalVotes() : 0;

        double currentTotal = currentRating * currentVotes;
        int newTotalVotes = currentVotes + 1;
        double newAverage = (currentTotal + rating) / newTotalVotes;

        movie.setRating(newAverage);
        movie.setTotalVotes(newTotalVotes);

        Movie savedMovie = movieRepository.save(movie);
        return modelMapper.map(savedMovie, MovieDTO.class);
    }
}
