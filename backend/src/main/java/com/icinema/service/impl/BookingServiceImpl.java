package com.icinema.service.impl;

import com.icinema.dto.BookingRequestDTO;
import com.icinema.dto.BookingResponseDTO;
import com.icinema.dto.PaymentDTO;
import com.icinema.entity.Booking;
import com.icinema.entity.Payment;
import com.icinema.entity.Seat;
import com.icinema.entity.Show;
import com.icinema.enums.CardType;
import com.icinema.enums.SeatStatus;
import com.icinema.exception.BadRequestException;
import com.icinema.exception.ResourceNotFoundException;
import com.icinema.repository.BookingRepository;
import com.icinema.repository.PaymentRepository;
import com.icinema.repository.SeatRepository;
import com.icinema.repository.ShowRepository;
import com.icinema.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import com.icinema.dto.BookingHistoryDTO;
import com.icinema.entity.User;

@Service
@RequiredArgsConstructor
@Log4j2
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;
    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;
    private final com.icinema.repository.UserRepository userRepository;

    private static final double CONVENIENCE_FEE = 50.0;
    private static final double GST_RATE = 0.18;

    @Override
    @Transactional
    public BookingResponseDTO initiateBooking(BookingRequestDTO bookingRequest) {
        log.info("Initiating booking for show id: {}", bookingRequest.getShowId());

        Show show = showRepository.findById(bookingRequest.getShowId())
                .orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        List<Seat> seats = seatRepository.findAllById(bookingRequest.getSeatIds());

        if (seats.size() != bookingRequest.getSeatIds().size()) {
            throw new BadRequestException("Some seats not found");
        }

        // Validate Status
        for (Seat seat : seats) {
            if (seat.getStatus() != SeatStatus.AVAILABLE) {
                throw new BadRequestException("Seat " + seat.getSeatNumber() + " is already booked or unavailable");
            }
        }

        // Calculate Cost
        double seatPriceTotal = seats.stream().mapToDouble(Seat::getPrice).sum();
        double totalBeforeTax = seatPriceTotal + CONVENIENCE_FEE;
        double gstAmount = totalBeforeTax * GST_RATE;
        double totalAmount = totalBeforeTax + gstAmount;

        // Create Booking
        Booking booking = Booking.builder()
                .show(show)
                .userEmail(bookingRequest.getUserEmail())
                .bookingTime(LocalDateTime.now())
                .totalAmount(totalAmount) // Initial amount before discount
                .convenienceFee(CONVENIENCE_FEE)
                .gst(gstAmount)
                .build();

        if (bookingRequest.getUserEmail() != null) {
            userRepository.findByEmail(bookingRequest.getUserEmail())
                    .ifPresent(booking::setUser);
        }

        Booking savedBooking = bookingRepository.save(booking);

        // Update Seats
        for (Seat seat : seats) {
            seat.setStatus(SeatStatus.BOOKED); // Lock seats
            seat.setBooking(savedBooking); // Link to booking
            seatRepository.save(seat);
        }

        BookingResponseDTO response = modelMapper.map(savedBooking, BookingResponseDTO.class);
        response.setBookingId(savedBooking.getId()); // Explicit mapping to ensure it's not null
        response.setStatus("PENDING");
        return response;
    }

    @Override
    @Transactional
    public BookingResponseDTO processPayment(PaymentDTO paymentRequest) {
        log.info("Processing payment for booking id: {}", paymentRequest.getBookingId());

        Booking booking = bookingRepository.findById(paymentRequest.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        // Discount Logic
        double originalAmount = booking.getTotalAmount();
        double discountRate = 0.0;
        if (paymentRequest.getCardType() == CardType.CREDIT) {
            discountRate = 0.10;
        } else if (paymentRequest.getCardType() == CardType.DEBIT) {
            discountRate = 0.05;
        }

        double discountAmount = originalAmount * discountRate;
        double finalAmount = originalAmount - discountAmount;

        // Create Payment
        Payment payment = Payment.builder()
                .booking(booking)
                .amount(finalAmount)
                .paymentTime(LocalDateTime.now())
                .cardType(paymentRequest.getCardType())
                .maskedCardNumber(maskCardNumber(paymentRequest.getCardNumber()))
                .build();

        paymentRepository.save(payment);

        // Update Booking logic? If we had a "PAID" status in Booking, we'd set it here.
        // For now, existence of Payment implies Paid.

        BookingResponseDTO response = modelMapper.map(booking, BookingResponseDTO.class);
        response.setTotalAmount(finalAmount); // Return paid amount
        response.setStatus("CONFIRMED");
        return response;
    }

    @Override
    public BookingResponseDTO getBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        return modelMapper.map(booking, BookingResponseDTO.class);
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4)
            return "****";
        return "************" + cardNumber.substring(cardNumber.length() - 4);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingHistoryDTO> getUserBookings(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return user.getBookings().stream().map(booking -> {
            // Collecting seat numbers
            String seatNos = booking.getSeats().stream()
                    .map(Seat::getSeatNumber)
                    .collect(java.util.stream.Collectors.joining(", "));

            return BookingHistoryDTO.builder()
                    .bookingId(booking.getId())
                    .movieTitle(booking.getShow().getMovie().getTitle())
                    .movieImage(booking.getShow().getMovie().getImageUrl())
                    .theatreName(booking.getShow().getTheatre().getName())
                    .showTime(LocalDateTime.of(booking.getShow().getShowDate(), booking.getShow().getShowTime()))
                    .numberOfSeats(booking.getSeats().size())
                    .seatNumbers(seatNos)
                    .totalAmount(booking.getTotalAmount())
                    .status("CONFIRMED") // Simplified status
                    .build();
        }).collect(java.util.stream.Collectors.toList());
    }
}
