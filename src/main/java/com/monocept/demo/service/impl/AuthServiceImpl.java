package com.monocept.demo.service.impl;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.monocept.demo.dto.request.LoginRequestDto;
import com.monocept.demo.dto.request.RegisterRequestDto;
import com.monocept.demo.dto.response.AuthResponseDto;
import com.monocept.demo.entity.User;
import com.monocept.demo.enums.Role;
import com.monocept.demo.exception.DuplicateResourceException;
import com.monocept.demo.exception.ForbiddenAccessException;
import com.monocept.demo.exception.ResourceNotFoundException;
import com.monocept.demo.repository.UserRepository;
import com.monocept.demo.security.CustomUserDetails;
import com.monocept.demo.security.JwtService;
import com.monocept.demo.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDto registerUser(RegisterRequestDto registerRequestDto) {

        if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
            throw new DuplicateResourceException(
                    "Email already registered");
        }

        User user = new User();

        user.setFullName(
                registerRequestDto.getFullName());

        user.setEmail(
                registerRequestDto.getEmail());

        user.setMobileNumber(
                registerRequestDto.getMobileNumber());

        user.setRole(
                registerRequestDto.getRole());

        user.setPassword(
                passwordEncoder.encode(
                        registerRequestDto.getPassword()));

        userRepository.save(user);

        return new AuthResponseDto(
                null,
                null,
                "User Registered Successfully");
    }

    @Override
    public AuthResponseDto loginUser(
            LoginRequestDto loginRequestDto) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequestDto.getEmail(),
                                loginRequestDto.getPassword()));

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        String token =
                jwtService.generateToken(userDetails);

        return new AuthResponseDto(
                token,
                "Bearer",
                userDetails.getUsername());
    }

    @Override
    public List<User> getAllUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        if(user.getRole() != Role.ADMIN) {
            throw new ForbiddenAccessException(
                    "Only admin can view all users");
        }

        return userRepository.findAll();
    }
}