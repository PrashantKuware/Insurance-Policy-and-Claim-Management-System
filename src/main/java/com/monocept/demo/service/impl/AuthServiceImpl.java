package com.monocept.demo.service.impl;

import org.springframework.stereotype.Service;

import com.monocept.demo.dto.request.LoginRequestDto;
import com.monocept.demo.dto.request.RegisterRequestDto;
import com.monocept.demo.dto.response.AuthResponseDto;
import com.monocept.demo.entity.User;
import com.monocept.demo.enums.Role;
import com.monocept.demo.exception.DuplicateResourceException;
import com.monocept.demo.exception.ResourceNotFoundException;
import com.monocept.demo.repository.UserRepository;
import com.monocept.demo.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;

	@Override
	public AuthResponseDto registerUser(RegisterRequestDto registerRequestDto) {
		if(userRepository.existsByEmail(registerRequestDto.getEmail())) {
		    throw new DuplicateResourceException("Email already registered");
		}
		User user = new User();
		user.setEmail(registerRequestDto.getEmail());
		user.setFullName(registerRequestDto.getFullName());
		user.setMobileNumber(registerRequestDto.getMobileNumber());
		user.setPassword(registerRequestDto.getPassword());
		user.setRole(Role.CUSTOMER);

		userRepository.save(user);

		return new AuthResponseDto("User Registered Successfully");
	}

	@Override
	public AuthResponseDto loginUser(LoginRequestDto loginRequestDto) {

		User user = userRepository.findByEmail(loginRequestDto.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		if(!user.getPassword().equals(loginRequestDto.getPassword())) {
		    throw new ResourceNotFoundException("Invalid credentials");
		}
		return new AuthResponseDto("User Login Successfully");
	}
}
