package com.icinema.service;

import com.icinema.dto.UserDto;
import com.icinema.entity.User;
import com.icinema.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto.UserResponse registerUser(UserDto.RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword()) // Storing plain text as allowed by plan
                .build();

        User savedUser = userRepository.save(user);

        return new UserDto.UserResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail(),
                savedUser.getPhone());
    }

    public UserDto.UserResponse loginUser(UserDto.LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return new UserDto.UserResponse(user.getId(), user.getName(), user.getEmail(), user.getPhone());
    }

    public UserDto.UserResponse updateUser(Long userId, UserDto.UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (request.getName() != null && !request.getName().isBlank()) {
            user.setName(request.getName());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }

        User updatedUser = userRepository.save(user);
        return new UserDto.UserResponse(updatedUser.getId(), updatedUser.getName(), updatedUser.getEmail(),
                updatedUser.getPhone());
    }
}
