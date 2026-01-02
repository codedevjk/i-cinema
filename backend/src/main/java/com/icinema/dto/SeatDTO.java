package com.icinema.dto;

import com.icinema.enums.SeatStatus;
import lombok.Data;

@Data
public class SeatDTO {
    private Long id;
    private String seatNumber;
    private SeatStatus status;
    private Double price;
}
