package com.monocept.demo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDto {

	@NotBlank(message = "Full name is required")
	private String fullName;

	@Email(message = "Invalid email format")
	@NotBlank(message = "Email is required")
	private String email;

	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Mobile number must be 10 digits")
	private String mobileNumber;

	@Size(min = 6, message = "Password must contain at least 6 characters")
	private String password;
}