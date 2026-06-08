package com.monocept.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.demo.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Page<Customer> findByAgentUserId(Long agentId, Pageable pageable);
}