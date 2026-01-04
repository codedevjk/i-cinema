package com.icinema.service.impl;

import com.icinema.dto.SeatDTO;
import com.icinema.entity.Show;
import com.icinema.enums.SeatStatus;
import com.icinema.repository.SeatRepository;
import com.icinema.repository.ShowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeatGenerationVerificationTest {

    @Mock
    private ShowRepository showRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ShowServiceImpl showService;

    private Show show;

    @BeforeEach
    void setUp() {
        show = new Show();
        show.setId(1L);
        show.setShowDate(LocalDate.now()); // Today
    }

    @Test
    void testGetSeatsForShow_WhenNoSeatsExist_ShouldGenerateCorrectMatrix() {
        // Arrange
        when(seatRepository.findByShowId(1L)).thenReturn(Collections.emptyList());
        when(showRepository.findById(1L)).thenReturn(Optional.of(show));

        // Act
        showService.getSeatsForShow(1L);

        // Assert
        ArgumentCaptor<List<com.icinema.entity.Seat>> captor = ArgumentCaptor.forClass(List.class);
        verify(seatRepository, atLeastOnce()).saveAll(captor.capture());

        List<com.icinema.entity.Seat> savedSeats = captor.getValue();
        assertEquals(100, savedSeats.size(), "Should generate 100 seats (A1-J10)");

        // Verify Matrix Logic
        // Row A (Standard)
        com.icinema.entity.Seat seatA1 = savedSeats.stream().filter(s -> s.getSeatNumber().equals("A1")).findFirst()
                .orElseThrow();
        assertEquals(180.0, seatA1.getPrice(), 0.01, "Row A should be 180.0");

        // Row H (Premium)
        com.icinema.entity.Seat seatH1 = savedSeats.stream().filter(s -> s.getSeatNumber().equals("H1")).findFirst()
                .orElseThrow();
        assertEquals(220.0, seatH1.getPrice(), 0.01, "Row H should be 220.0");

        // Verify Status (Today = ~50% booked)
        long bookedCount = savedSeats.stream().filter(s -> s.getStatus() == SeatStatus.BOOKED).count();
        System.out.println("Generated Booked Seats count: " + bookedCount);
        assertTrue(bookedCount > 20 && bookedCount < 80, "Booked seats should be roughly 50% (allow random range)");
    }
}
