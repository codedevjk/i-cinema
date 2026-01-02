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
@CrossOrigin(origins = "http://localhost:4200") // Angular default port
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
    public ResponseEntity<MovieDTO> rateMovie(@PathVariable Long id, @RequestParam Integer rating) {
        if (rating < 1 || rating > 10) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(movieService.rateMovie(id, rating));
    }
}
