package com.icinema.controller;

import com.icinema.dto.BookingRequestDTO;
import com.icinema.dto.BookingResponseDTO;
import com.icinema.dto.PaymentDTO;
import com.icinema.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponseDTO> initiateBooking(@RequestBody BookingRequestDTO request) {
        return new ResponseEntity<>(bookingService.initiateBooking(request), HttpStatus.CREATED);
    }

    @PostMapping("/payment")
    public ResponseEntity<BookingResponseDTO> processPayment(@Valid @RequestBody PaymentDTO request) {
        return ResponseEntity.ok(bookingService.processPayment(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBooking(id));
    }
}
