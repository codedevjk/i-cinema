package com.icinema.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingResponseDTO {
    private Long bookingId;
    private Double totalAmount;
    private LocalDateTime bookingTime;
    private String status; // Pending/Confirmed
}
