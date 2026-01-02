package com.icinema.entity;

import com.icinema.enums.Genre;
import com.icinema.enums.Language;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer durationInMinutes;

    private Double rating; // US 10: 1-5 star rating

    // For calculating average rating, we might need a separate Rating entity or
    // just store count and total.
    // For simplicity of "update movie's average", we can store average and
    // totalVotes.
    @Builder.Default
    private Integer totalVotes = 0;

    @Column(length = 500)
    private String imageUrl;

    @Column(length = 10)
    private String censorRating; // e.g., "UA", "A", "U"
}
