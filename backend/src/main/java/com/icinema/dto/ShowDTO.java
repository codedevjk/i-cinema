package com.icinema.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ShowDTO {
    private Long id;
    private Long movieId; // Flattened ID for frontend convenience
    private MovieDTO movie;
    private Long theatreId; // Or TheatreDTO
    private String theatreName;
    private LocalDate showDate;
    private LocalTime showTime;
    private String location;
    private Double ticketPrice;
}
