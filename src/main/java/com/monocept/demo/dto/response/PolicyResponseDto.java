package com.monocept.demo.dto.response;

import lombok.Data;

@Data
public class PolicyResponseDto {

    private Long policyId;

    private String policyNumber;

    private String customerName;

    private String planName;

    private String policyStatus;
}