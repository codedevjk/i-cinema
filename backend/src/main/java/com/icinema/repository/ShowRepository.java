package com.icinema.repository;

import com.icinema.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
    List<Show> findByMovieIdAndShowDate(Long movieId, LocalDate showDate);

    List<Show> findByMovieId(Long movieId);
}
