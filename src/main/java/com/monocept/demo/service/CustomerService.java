package com.monocept.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.monocept.demo.dto.request.CustomerRequestDto;
import com.monocept.demo.dto.response.CustomerResponseDto;

@Service
public interface CustomerService {

	CustomerResponseDto createCustomer(Long userId, CustomerRequestDto customerRequestDto);
	
    List<CustomerResponseDto> getAllCustomers();

    CustomerResponseDto getCustomerById(Long customerId);
    
    CustomerResponseDto updateCustomer(Long customerId, CustomerRequestDto customerRequestDto);
    
    void deleteCustomer(Long customerId);
}
