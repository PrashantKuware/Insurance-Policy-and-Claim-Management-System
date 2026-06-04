package com.monocept.demo.dto.response;

import lombok.Data;

@Data
public class ProductResponseDto {

    private Long productId;

    private String productName;

    private String productType;

    private String description;

    private Boolean active;
}