package com.icinema.service;

import com.icinema.dto.SeatDTO;
import com.icinema.dto.ShowDTO;
import java.util.List;

public interface ShowService {
    List<ShowDTO> getShowsByMovie(Long movieId);

    ShowDTO getShowById(Long id);

    List<SeatDTO> getSeatsForShow(Long showId);
}
