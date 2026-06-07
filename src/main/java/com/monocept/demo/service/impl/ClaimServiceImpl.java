package com.monocept.demo.service.impl;


import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.monocept.demo.dto.request.ClaimDecisionRequestDto;
import com.monocept.demo.dto.request.ClaimRecommendationRequestDto;
import com.monocept.demo.dto.request.ClaimRequestDto;
import com.monocept.demo.dto.request.ClaimReviewRequestDto;
import com.monocept.demo.dto.response.ClaimResponseDto;
import com.monocept.demo.entity.Claim;
import com.monocept.demo.entity.Policy;
import com.monocept.demo.enums.ClaimStatus;
import com.monocept.demo.enums.PolicyStatus;
import com.monocept.demo.exception.InvalidClaimStatusException;
import com.monocept.demo.exception.InvalidPolicyStatusException;
import com.monocept.demo.exception.ResourceNotFoundException;
import com.monocept.demo.repository.ClaimRepository;
import com.monocept.demo.repository.PolicyRepository;
import com.monocept.demo.service.ClaimService;
import com.monocept.demo.service.ClaimStatusHistoryService;


@Service
public class ClaimServiceImpl implements ClaimService {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private ClaimStatusHistoryService historyService;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ClaimResponseDto recommendClaimForApproval(
            Long claimId,
            ClaimRecommendationRequestDto requestDto) {

        Claim claim = getClaimEntity(claimId);

        if(claim.getClaimStatus() != ClaimStatus.UNDER_REVIEW) {
            throw new InvalidClaimStatusException(
                    "Claim must be under review");
        }

        ClaimStatus oldStatus = claim.getClaimStatus();

        claim.setClaimStatus(
                ClaimStatus.RECOMMENDED_FOR_APPROVAL);

        claim.setAgentRemarks(
                requestDto.getRemarks());

        claim.setUpdatedDate(
                LocalDateTime.now());

        claimRepository.save(claim);

        historyService.saveStatusHistory(
                claimId,
                oldStatus,
                ClaimStatus.RECOMMENDED_FOR_APPROVAL,
                requestDto.getRemarks());

        return mapper.map(claim,
                ClaimResponseDto.class);
    }
    
    @Override
    public ClaimResponseDto recommendClaimForRejection(
            Long claimId,
            ClaimRecommendationRequestDto requestDto) {

        Claim claim = getClaimEntity(claimId);

        if(claim.getClaimStatus() != ClaimStatus.UNDER_REVIEW) {
            throw new InvalidClaimStatusException(
                    "Claim must be under review");
        }

        ClaimStatus oldStatus = claim.getClaimStatus();

        claim.setClaimStatus(
                ClaimStatus.RECOMMENDED_FOR_REJECTION);

        claim.setAgentRemarks(
                requestDto.getRemarks());

        claim.setUpdatedDate(
                LocalDateTime.now());

        claimRepository.save(claim);

        historyService.saveStatusHistory(
                claimId,
                oldStatus,
                ClaimStatus.RECOMMENDED_FOR_REJECTION,
                requestDto.getRemarks());

        return mapper.map(claim,
                ClaimResponseDto.class);
    }
    
    @Override
    public ClaimResponseDto submitClaim(Long policyId,
                                        ClaimRequestDto requestDto) {

        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Policy not found"));

        if(policy.getPolicyStatus() != PolicyStatus.ACTIVE)
        {
            throw new InvalidPolicyStatusException(
                    "Claim can only be raised for active policies");
        }

        Claim claim = new Claim();


        claim.setPolicy(policy);

        claim.setClaimAmount(
                requestDto.getClaimAmount());

        claim.setClaimReason(
                requestDto.getClaimReason());

        claim.setIncidentDate(
                requestDto.getIncidentDate());

        claim.setClaimStatus(
                ClaimStatus.SUBMITTED);

        claim.setCreatedDate(
                LocalDateTime.now());

        claim.setUpdatedDate(
                LocalDateTime.now());

        claim = claimRepository.save(claim);

        historyService.saveStatusHistory(
                claim.getClaimId(),
                null,
                ClaimStatus.SUBMITTED,
                "Claim Submitted");

        return mapper.map(claim,
                ClaimResponseDto.class);
    }

    @Override
    public ClaimResponseDto reviewClaim(
            Long claimId,
            ClaimReviewRequestDto requestDto) {

        Claim claim = getClaimEntity(claimId);

        if(claim.getClaimStatus() != ClaimStatus.SUBMITTED)
        {
            throw new InvalidClaimStatusException(
                    "Only submitted claims can be reviewed");
        }

        ClaimStatus oldStatus =
                claim.getClaimStatus();

        claim.setClaimStatus(
                ClaimStatus.UNDER_REVIEW);

        claim.setAgentRemarks(
                requestDto.getRemarks());

        claim.setUpdatedDate(
                LocalDateTime.now());

        claimRepository.save(claim);

        historyService.saveStatusHistory(
                claimId,
                oldStatus,
                ClaimStatus.UNDER_REVIEW,
                requestDto.getRemarks());

        return mapper.map(claim,
                ClaimResponseDto.class);
    }

   
    
    @Override
    public ClaimResponseDto approveClaim(
            Long claimId,
            ClaimDecisionRequestDto requestDto) {

        Claim claim = getClaimEntity(claimId);

        if(claim.getClaimStatus()
                != ClaimStatus.RECOMMENDED_FOR_APPROVAL) {

            throw new InvalidClaimStatusException(
                    "Claim must be recommended for approval");
        }

        ClaimStatus oldStatus =
                claim.getClaimStatus();

        claim.setClaimStatus(
                ClaimStatus.APPROVED);

        claim.setAdminRemarks(
                requestDto.getRemarks());

        claim.setUpdatedDate(
                LocalDateTime.now());

        claimRepository.save(claim);

        historyService.saveStatusHistory(
                claimId,
                oldStatus,
                ClaimStatus.APPROVED,
                requestDto.getRemarks());

        return mapper.map(claim,
                ClaimResponseDto.class);
    }

    @Override
    public ClaimResponseDto rejectClaim(
            Long claimId,
            ClaimDecisionRequestDto requestDto) {

        Claim claim = getClaimEntity(claimId);

        if(claim.getClaimStatus()
                != ClaimStatus.RECOMMENDED_FOR_REJECTION) {

            throw new InvalidClaimStatusException(
                    "Claim must be recommended for rejection");
        }

        ClaimStatus oldStatus =
                claim.getClaimStatus();

        claim.setClaimStatus(
                ClaimStatus.REJECTED);

        claim.setAdminRemarks(
                requestDto.getRemarks());

        claim.setUpdatedDate(
                LocalDateTime.now());

        claimRepository.save(claim);

        historyService.saveStatusHistory(
                claimId,
                oldStatus,
                ClaimStatus.REJECTED,
                requestDto.getRemarks());

        return mapper.map(claim,
                ClaimResponseDto.class);
    }

    @Override
    public ClaimResponseDto getClaimById(Long claimId) {

        Claim claim = getClaimEntity(claimId);

        return mapper.map(claim,
                ClaimResponseDto.class);
    }

    @Override
    public ClaimResponseDto getClaimByClaimNumber(
            String claimNumber) {

        Claim claim = claimRepository
                .findByClaimNumber(claimNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Claim not found"));

        return mapper.map(claim,
                ClaimResponseDto.class);
    }

    @Override
    public Page<ClaimResponseDto> getClaimsByCustomer(
            Long customerId,
            Pageable pageable) {

    	return claimRepository
    	        .findByPolicy_Customer_CustomerId(
    	                customerId,
    	                pageable)
    	        .map(claim ->
    	                mapper.map(claim,
    	                        ClaimResponseDto.class));
    }

    @Override
    public Page<ClaimResponseDto> getAllClaims(
            Pageable pageable) {

        return claimRepository
                .findAll(pageable)
                .map(claim ->
                        mapper.map(claim,
                                ClaimResponseDto.class));
    }

    private Claim getClaimEntity(Long claimId) {

        return claimRepository.findById(claimId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Claim not found"));
    }
}