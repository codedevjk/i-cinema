package com.icinema.service.impl;

import com.icinema.dto.SeatDTO;
import com.icinema.entity.Movie;
import com.icinema.entity.Show;
import com.icinema.entity.Theatre;
import com.icinema.repository.SeatRepository;
import com.icinema.repository.ShowRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShowServiceImplTest {

    @Mock
    private ShowRepository showRepository;

    @Mock
    private SeatRepository seatRepository;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private ShowServiceImpl showService;

    @Test
    public void testGetSeatsForShow_GeneratesSeats() {
        // Mock Show
        Show show = new Show();
        show.setId(1L);
        show.setShowDate(LocalDate.now().plusDays(1));
        show.setShowTime(LocalTime.of(10, 0));
        show.setMovie(new Movie());
        show.setTheatre(new Theatre());

        when(showRepository.findById(1L)).thenReturn(Optional.of(show));
        when(seatRepository.findByShowId(1L)).thenReturn(Collections.emptyList()).thenReturn(Collections.emptyList());
        // Note: The second return should ideally return the saved seats, but for void
        // method testing we just want to ensure code flow doesn't crash.
        // Actually generateSeatsForShow calls saveAll. We can mock that.

        try {
            showService.getSeatsForShow(1L);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        verify(seatRepository, atLeastOnce()).saveAll(any());
        verify(seatRepository, atLeastOnce()).findByShowId(1L);
    }
}
