package com.monocept.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.demo.entity.ClaimStatusHistory;

public interface ClaimStatusHistoryRepository extends JpaRepository<ClaimStatusHistory, Long>{

}
