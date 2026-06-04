package com.monocept.demo.dto.request;

import com.monocept.demo.enums.ProductType;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductRequestDto {

    @NotBlank
    private String productName;

    private ProductType productType;

    private String description;
}