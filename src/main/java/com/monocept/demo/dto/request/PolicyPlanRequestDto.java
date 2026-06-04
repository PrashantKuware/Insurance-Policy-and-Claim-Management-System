package com.monocept.demo.dto.request;

import java.math.BigDecimal;

import com.monocept.demo.enums.PremiumType;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PolicyPlanRequestDto {

    @NotNull
    private Long productId;

    private String planName;

    private BigDecimal coverageAmount;

    private BigDecimal premiumAmount;

    private PremiumType premiumType;

    private Integer duration;

    private String termsConditions;
}