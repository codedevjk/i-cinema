package com.icinema.service;

import com.icinema.dto.BookingRequestDTO;
import com.icinema.dto.BookingResponseDTO;
import com.icinema.dto.PaymentDTO;

public interface BookingService {
    BookingResponseDTO initiateBooking(BookingRequestDTO bookingRequest);

    BookingResponseDTO processPayment(PaymentDTO paymentRequest);

    BookingResponseDTO getBooking(Long bookingId);

    java.util.List<com.icinema.dto.BookingHistoryDTO> getUserBookings(Long userId);
}
