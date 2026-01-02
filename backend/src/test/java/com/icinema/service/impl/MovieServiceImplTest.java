package com.icinema.service.impl;

import com.icinema.dto.MovieDTO;
import com.icinema.entity.Movie;
import com.icinema.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private com.icinema.repository.UserRepository userRepository;

    @Mock
    private com.icinema.repository.RatingRepository ratingRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Test
    void rateMovie_ShouldCalculateAverageCorrectly_FirstVote() {
        // Arrange
        Long movieId = 1L;
        Long userId = 100L;
        Integer newRating = 5;

        // Movie with no prior ratings
        Movie existingMovie = Movie.builder()
                .id(movieId)
                .title("Test Movie")
                .rating(0.0)
                .totalVotes(0)
                .build();

        // Mock User
        com.icinema.entity.User user = new com.icinema.entity.User();
        user.setId(userId);

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(existingMovie));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(ratingRepository.findByUserIdAndMovieId(userId, movieId)).thenReturn(Optional.empty());

        when(movieRepository.save(any(Movie.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(modelMapper.map(any(Movie.class), eq(MovieDTO.class))).thenAnswer(invocation -> {
            Movie m = invocation.getArgument(0);
            MovieDTO dto = new MovieDTO();
            dto.setId(m.getId());
            dto.setRating(m.getRating());
            dto.setTotalVotes(m.getTotalVotes());
            return dto;
        });

        // Act
        MovieDTO result = movieService.rateMovie(movieId, userId, newRating);

        // Assert
        Assertions.assertEquals(5.0, result.getRating());
        Assertions.assertEquals(1, result.getTotalVotes());
    }

    @Test
    void rateMovie_ShouldUpdateAverageCorrectly_SubsequentVote() {
        // Arrange
        Long movieId = 1L;
        Long userId = 101L;
        Integer newRating = 4; // Adding a 4-star rating

        // Existing: Average 5.0 from 1 vote
        Movie existingMovie = Movie.builder()
                .id(movieId)
                .title("Test Movie")
                .rating(5.0)
                .totalVotes(1)
                .build();

        // Mock User
        com.icinema.entity.User user = new com.icinema.entity.User();
        user.setId(userId);

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(existingMovie));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(ratingRepository.findByUserIdAndMovieId(userId, movieId)).thenReturn(Optional.empty());

        when(movieRepository.save(any(Movie.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(modelMapper.map(any(Movie.class), eq(MovieDTO.class))).thenAnswer(invocation -> {
            Movie m = invocation.getArgument(0);
            MovieDTO dto = new MovieDTO();
            dto.setId(m.getId());
            dto.setRating(m.getRating());
            dto.setTotalVotes(m.getTotalVotes());
            return dto;
        });

        // Act
        MovieDTO result = movieService.rateMovie(movieId, userId, newRating);

        // Assert
        // New Average = (5.0 * 1 + 4) / 2 = 9 / 2 = 4.5
        Assertions.assertEquals(4.5, result.getRating());
        Assertions.assertEquals(2, result.getTotalVotes());
    }
}
