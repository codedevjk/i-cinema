package com.icinema.controller;

import com.icinema.dto.MovieDTO;
import com.icinema.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieDTO>> searchMovies(@RequestParam String title) {
        return ResponseEntity.ok(movieService.searchMovies(title));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @PostMapping("/{id}/rate")
    public ResponseEntity<?> rateMovie(
            @PathVariable Long id,
            @RequestParam Long userId,
            @RequestParam Integer rating) {
        if (rating < 1 || rating > 10) {
            return ResponseEntity.badRequest().body("Rating must be between 1 and 10");
        }
        try {
            return ResponseEntity.ok(movieService.rateMovie(id, userId, rating));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/user-rating")
    public ResponseEntity<Integer> getUserRating(@PathVariable Long id, @RequestParam Long userId) {
        return ResponseEntity.ok(movieService.getUserRating(id, userId));
    }
}
