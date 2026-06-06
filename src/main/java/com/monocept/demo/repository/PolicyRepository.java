package com.monocept.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.demo.entity.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Long>{

}
