package com.icinema.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingHistoryDTO {
    private Long bookingId;
    private String movieTitle;
    private String movieImage;
    private String theatreName;
    private LocalDateTime showTime;
    private int numberOfSeats;
    private String seatNumbers; // stored as comma separated string or formatted
    private Double totalAmount;
    private String status; // Placeholder for now
}
