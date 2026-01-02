package com.icinema.dto;

import com.icinema.enums.Genre;
import com.icinema.enums.Language;
import lombok.Data;

@Data
public class MovieDTO {
    private Long id;
    private String title;
    private Genre genre;
    private Language language;
    private String description;
    private Integer durationInMinutes;
    private Double rating;
    private String imageUrl;
    private String censorRating;
    private Integer totalVotes;
}
