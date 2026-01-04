package com.icinema.config;

import com.icinema.entity.Movie;
import com.icinema.entity.Show;
import com.icinema.entity.Theatre;
import com.icinema.repository.MovieRepository;
import com.icinema.repository.ShowRepository;
import com.icinema.repository.TheatreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final TheatreRepository theatreRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Scheduled(cron = "0 0 0 * * *") // Run daily at midnight
    public void generateShowsIfNeeded() {
        log.info("Checking if show generation is needed...");

        List<Movie> movies = movieRepository.findAll();
        List<Theatre> theatres = theatreRepository.findAll();

        if (movies.isEmpty() || theatres.isEmpty()) {
            log.warn("No movies or theatres found. Skipping show generation.");
            return;
        }

        LocalDate today = LocalDate.now();
        Random random = new Random();

        // Check for Today, Tomorrow, and Day After Tomorrow
        for (int i = 0; i < 3; i++) {
            LocalDate targetDate = today.plusDays(i);
            generateShowsForDate(targetDate, movies, theatres, random, i);
        }
    }

    private void generateShowsForDate(LocalDate date, List<Movie> movies, List<Theatre> theatres, Random random,
            int dayIndex) {
        List<Show> existingShows = showRepository.findByShowDate(date);

        if (!existingShows.isEmpty()) {
            log.info("Shows already exist for {}. Skipping generation.", date);
            return;
        }

        log.info("Generating shows for {}...", date);

        LocalTime[] showTimes = {
                LocalTime.of(9, 0),
                LocalTime.of(10, 15),
                LocalTime.of(12, 30),
                LocalTime.of(14, 45),
                LocalTime.of(16, 0),
                LocalTime.of(18, 30),
                LocalTime.of(20, 15),
                LocalTime.of(22, 0)
        };

        // Probabilities based on day index (0=Today, 1=Tomorrow, 2=DayAfter)
        // Today: ~3-4 shows -> 45% chance
        // Tomorrow: ~2-3 shows -> 30% chance
        // Day After: ~0-2 shows -> 15% chance
        double probability = 0.45;
        if (dayIndex == 1)
            probability = 0.30;
        if (dayIndex == 2)
            probability = 0.15;

        int count = 0;
        for (Theatre theatre : theatres) {
            // Revised approach to avoid massive "multiple movies per slot" issue while
            // keeping it simple:
            // Loop Theatre -> Loop Time Slot
            // With probability P, we decide to schedule a show.
            // If scheduled, pick a RANDOM movie.

            for (LocalTime time : showTimes) {
                if (random.nextDouble() < probability) {
                    Movie randomMovie = movies.get(random.nextInt(movies.size()));

                    Show show = Show.builder()
                            .movie(randomMovie)
                            .theatre(theatre)
                            .showDate(date)
                            .showTime(time)
                            .build();

                    showRepository.save(show);
                    count++;
                }
            }
        }
        log.info("Generated {} shows for {}.", count, date);
    }
}
