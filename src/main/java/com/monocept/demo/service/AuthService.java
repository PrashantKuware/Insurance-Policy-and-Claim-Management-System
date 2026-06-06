package com.monocept.demo.service;

import org.springframework.stereotype.Service;

import com.monocept.demo.dto.request.LoginRequestDto;
import com.monocept.demo.dto.request.RegisterRequestDto;
import com.monocept.demo.dto.response.AuthResponseDto;

@Service
public interface AuthService {

	AuthResponseDto registerUser(RegisterRequestDto registerRequestDto);
	
	AuthResponseDto loginUser(LoginRequestDto loginRequestDto);
}
