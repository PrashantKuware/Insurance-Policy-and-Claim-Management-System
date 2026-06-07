package com.monocept.demo.repository;

import org.springframework.data.domain.Pageable;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.demo.entity.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Long>
{
	 Optional<Claim> findByClaimNumber(
	            String claimNumber);

	 Page<Claim> findByPolicy_Customer_CustomerId(
		        Long customerId,
		        Pageable pageable);
}
