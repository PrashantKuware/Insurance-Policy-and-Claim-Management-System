package com.monocept.demo.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ClaimRequestDto {

    private Long policyId;

    private BigDecimal claimAmount;

    private String claimReason;

    private LocalDate incidentDate;
}