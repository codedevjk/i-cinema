package com.icinema.service.impl;

import com.icinema.dto.SeatDTO;
import com.icinema.dto.ShowDTO;
import com.icinema.entity.Show;
import com.icinema.exception.ResourceNotFoundException;
import com.icinema.repository.SeatRepository;
import com.icinema.repository.ShowRepository;
import com.icinema.service.ShowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ShowDTO> getShowsByMovie(Long movieId) {
        log.info("Fetching shows for movie id: {}", movieId);
        return showRepository.findByMovieId(movieId).stream()
                .map(show -> {
                    ShowDTO dto = modelMapper.map(show, ShowDTO.class);
                    // Manually populate fields that might be missed by ModelMapper
                    if (show.getTheatre() != null) {
                        dto.setTheatreName(show.getTheatre().getName());
                        dto.setLocation(show.getTheatre().getLocation());
                    }
                    if (show.getMovie() != null) {
                        dto.setMovieId(show.getMovie().getId());
                    }
                    // Set default price or logic to fetch min price
                    dto.setTicketPrice(180.0);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ShowDTO getShowById(Long id) {
        log.info("Fetching show with id: {}", id);
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found"));
        ShowDTO dto = modelMapper.map(show, ShowDTO.class);
        if (show.getMovie() != null) {
            dto.setMovieId(show.getMovie().getId());
        }
        return dto;
    }

    @Override
    public List<SeatDTO> getSeatsForShow(Long showId) {
        try {
            log.info("Fetching seats for show id: {}", showId);
            List<com.icinema.entity.Seat> seats = seatRepository.findByShowId(showId);

            boolean needsUpdate = false;

            // 1. Check for legacy prices (280.0) or incorrect data
            if (seats != null && !seats.isEmpty()) {
                for (com.icinema.entity.Seat seat : seats) {
                    if (seat.getSeatNumber() == null)
                        continue;

                    char row = seat.getSeatNumber().charAt(0);
                    double expectedPrice = (row >= 'H') ? 220.0 : 180.0;

                    if (Math.abs(seat.getPrice() - expectedPrice) > 0.01) {
                        log.info("Fixing price for seat {}: old={}, new={}", seat.getSeatNumber(), seat.getPrice(),
                                expectedPrice);
                        seat.setPrice(expectedPrice);
                        needsUpdate = true;
                    }
                }
            }

            // 2. Save updates if needed
            if (needsUpdate) {
                seatRepository.saveAll(seats);
            }

            // 3. Generate missing seats if incomplete (e.g., < 100)
            if (seats == null || seats.isEmpty() || seats.size() < 100) {
                log.info("Insufficient seats found for show id: {}. Generating/Completing.", showId);

                List<SeatDTO> existingDTOs = (seats != null)
                        ? seats.stream().map(s -> modelMapper.map(s, SeatDTO.class))
                                .collect(Collectors.toList())
                        : java.util.Collections.emptyList();
                generateSeatsForShow(showId, existingDTOs);

                // Re-fetch after generation
                seats = seatRepository.findByShowId(showId);
            }

            return seats.stream()
                    .map(seat -> {
                        SeatDTO dto = new SeatDTO();
                        dto.setId(seat.getId());
                        dto.setSeatNumber(seat.getSeatNumber());
                        dto.setPrice(seat.getPrice());
                        dto.setStatus(seat.getStatus());
                        return dto;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error in getSeatsForShow: ", e);
            throw e;
        }
    }

    private void generateSeatsForShow(Long showId, List<SeatDTO> existing) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        java.util.List<com.icinema.entity.Seat> seatsToSave = new java.util.ArrayList<>();

        // Determine booking probability based on date
        java.time.LocalDate today = java.time.LocalDate.now();
        long daysDiff = java.time.temporal.ChronoUnit.DAYS.between(today, show.getShowDate());

        double bookingProbability = 0.05; // Default 5%
        if (daysDiff <= 0) {
            bookingProbability = 0.50; // Today: 50% booked
        } else if (daysDiff == 1) {
            bookingProbability = 0.30; // Tomorrow: 30% booked
        } else if (daysDiff == 2) {
            bookingProbability = 0.15; // Day After: 15% booked
        }

        char[] rows = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };

        for (char row : rows) {
            double price = (row >= 'H') ? 220.0 : 180.0;

            for (int i = 1; i <= 10; i++) {
                String seatNum = String.valueOf(row) + i;

                // Skip if already exists
                if (existing != null && existing.stream().anyMatch(s -> s.getSeatNumber().equals(seatNum))) {
                    continue;
                }

                // Random occupany logic
                com.icinema.enums.SeatStatus status = com.icinema.enums.SeatStatus.AVAILABLE;
                if (Math.random() < bookingProbability) {
                    status = com.icinema.enums.SeatStatus.BOOKED;
                }

                com.icinema.entity.Seat seat = com.icinema.entity.Seat.builder()
                        .seatNumber(seatNum)
                        .price(price)
                        .status(status)
                        .show(show)
                        .build();
                seatsToSave.add(seat);
            }
        }
        if (!seatsToSave.isEmpty()) {
            seatRepository.saveAll(seatsToSave);
        }
    }
}
