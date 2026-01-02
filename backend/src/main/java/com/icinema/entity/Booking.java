package com.icinema.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true) // Nullable for guest bookings if supported, or false if forced
    private User user;

    // Kept for backward compatibility or guest users if necessary,
    // but we should primarily use the User relation for registered users.
    @Column(nullable = true)
    private String userEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Seat> seats;

    @Column(nullable = false)
    private Double totalAmount;

    @Column(name = "convenience_fee")
    private Double convenienceFee;

    @Column(name = "gst")
    private Double gst;

    @Column(name = "booking_time", nullable = false)
    private LocalDateTime bookingTime;

    // Default to PENDING, can be updated to CONFIRMED after payment.
    // For simplicity, we might assume created = confirmed if payment is immediate
    // in transaction.
    // But let's keep it simple.
}
