package com.monocept.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monocept.demo.dto.request.LoginRequestDto;
import com.monocept.demo.dto.request.RegisterRequestDto;
import com.monocept.demo.dto.response.AuthResponseDto;
import com.monocept.demo.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public AuthResponseDto register(@Valid @RequestBody RegisterRequestDto request) {

		return authService.registerUser(request);

	}

	@PostMapping("/login")
	public AuthResponseDto login(@Valid @RequestBody LoginRequestDto request) {

		return authService.loginUser(request);
	}

}
