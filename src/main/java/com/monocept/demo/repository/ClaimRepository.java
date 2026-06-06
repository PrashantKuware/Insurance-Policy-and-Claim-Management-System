package com.monocept.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.demo.entity.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Long>{

}
