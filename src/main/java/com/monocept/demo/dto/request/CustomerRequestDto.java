package com.monocept.demo.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerRequestDto {

    private LocalDate dateOfBirth;

    @NotBlank
    private String address;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String pinCode;

    @NotBlank
    private String nomineeName;

    @NotBlank
    private String nomineeRelation;
}