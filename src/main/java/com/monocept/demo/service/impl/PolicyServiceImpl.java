package com.monocept.demo.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.monocept.demo.dto.request.PolicyPurchaseRequestDto;
import com.monocept.demo.dto.response.PolicyResponseDto;
import com.monocept.demo.entity.Customer;
import com.monocept.demo.entity.Policy;
import com.monocept.demo.entity.PolicyPlan;
import com.monocept.demo.enums.PolicyStatus;
import com.monocept.demo.exception.ResourceNotFoundException;
import com.monocept.demo.repository.CustomerRepository;
import com.monocept.demo.repository.PolicyPlanRepository;
import com.monocept.demo.repository.PolicyRepository;
import com.monocept.demo.service.PolicyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PolicyServiceImpl implements PolicyService {

	private final PolicyRepository policyRepository;
	private final CustomerRepository customerRepository;
	private final PolicyPlanRepository planRepository;

	@Override
	public PolicyResponseDto purchasePolicy(PolicyPurchaseRequestDto dto) {

		Customer customer = customerRepository.findById(dto.getCustomerId())
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

		PolicyPlan plan = planRepository.findById(dto.getPlanId())
				.orElseThrow(() -> new ResourceNotFoundException("Plan not found"));

		if (plan.getActive() == false) {
			throw new IllegalStateException("Plan is inactive");
		}

		Policy policy = Policy.builder().policyNumber("POL" + System.currentTimeMillis()).customer(customer)
				.policyPlan(plan).policyStatus(PolicyStatus.PENDING_PAYMENT).totalPremiumPaid(BigDecimal.ZERO)
				.createdDate(LocalDateTime.now()).build();

		policyRepository.save(policy);

		return mapToResponse(policy);
	}

	@Override
	public List<PolicyResponseDto> getPoliciesByCustomer(Long customerId) {

		List<Policy> policies = policyRepository.findByCustomerCustomerId(customerId);

		List<PolicyResponseDto> response = new ArrayList<>();

		for (Policy policy : policies) {
			response.add(mapToResponse(policy));
		}

		return response;
	}

	@Override
	public PolicyResponseDto getPolicyById(Long policyId) {

		Policy policy = policyRepository.findById(policyId)
				.orElseThrow(() -> new ResourceNotFoundException("Policy not found"));

		return mapToResponse(policy);
	}

	private PolicyResponseDto mapToResponse(Policy policy) {

		return PolicyResponseDto.builder().policyId(policy.getPolicyId()).policyNumber(policy.getPolicyNumber())
				.customerName(policy.getCustomer().getUser().getFullName())
				.planName(policy.getPolicyPlan().getPlanName()).startDate(policy.getStartDate())
				.endDate(policy.getEndDate()).policyStatus(policy.getPolicyStatus())
				.totalPremiumPaid(policy.getTotalPremiumPaid()).build();
	}

	@Override
	public PolicyResponseDto issuePolicy(Long customerId, Long planId) {

		PolicyPurchaseRequestDto dto = new PolicyPurchaseRequestDto();

		dto.setCustomerId(customerId);
		dto.setPlanId(planId);

		return purchasePolicy(dto);
	}

	@Override
	public Page<PolicyResponseDto> getAllPolicies(int pageNo, int pageSize, String sortBy) {

		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		return policyRepository.findAll(pageable).map(this::mapToResponse);
	}

	@Override
	public void cancelPolicy(Long policyId) {

		Policy policy = policyRepository.findById(policyId)
				.orElseThrow(() -> new ResourceNotFoundException("Policy not found"));

		policy.setPolicyStatus(PolicyStatus.CANCELLED);

		policy.setUpdatedDate(LocalDateTime.now());

		policyRepository.save(policy);
	}

	@Override
	public void activatePolicy(Long policyId) {

		Policy policy = policyRepository.findById(policyId)
				.orElseThrow(() -> new ResourceNotFoundException("Policy not found"));

		policy.setPolicyStatus(PolicyStatus.ACTIVE);

		policy.setStartDate(LocalDate.now());

		policy.setEndDate(LocalDate.now().plusYears(policy.getPolicyPlan().getDuration()));

		policy.setUpdatedDate(LocalDateTime.now());

		policyRepository.save(policy);
	}
}