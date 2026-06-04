package com.monocept.demo.dto.response;

import lombok.Data;

@Data
public class PolicyPlanResponseDto {

    private Long planId;

    private String productName;

    private String planName;

    private Double coverageAmount;

    private Double premiumAmount;

    private String premiumType;

    private Integer duration;
}