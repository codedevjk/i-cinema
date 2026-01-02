package com.icinema.dto;

import lombok.Data;
import java.util.List;

@Data
public class BookingRequestDTO {
    private Long showId;
    private List<Long> seatIds;
    private String userEmail;
}
