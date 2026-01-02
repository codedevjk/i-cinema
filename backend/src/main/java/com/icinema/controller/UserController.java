package com.icinema.controller;

import com.icinema.dto.BookingHistoryDTO;
import com.icinema.dto.UserDto;
import com.icinema.service.BookingService;
import com.icinema.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BookingService bookingService;

    @PostMapping("/register")
    public ResponseEntity<UserDto.UserResponse> register(@Valid @RequestBody UserDto.RegisterRequest request) {
        return ResponseEntity.ok(userService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto.UserResponse> login(@Valid @RequestBody UserDto.LoginRequest request) {
        return ResponseEntity.ok(userService.loginUser(request));
    }

    @GetMapping("/{userId}/bookings")
    public ResponseEntity<List<BookingHistoryDTO>> getUserBookings(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getUserBookings(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto.UserResponse> updateUser(@PathVariable Long userId,
            @RequestBody UserDto.UpdateProfileRequest request) {
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }
}
