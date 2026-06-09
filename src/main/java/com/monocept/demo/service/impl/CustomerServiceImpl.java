package com.monocept.demo.service.impl;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.monocept.demo.dto.request.CustomerRequestDto;
import com.monocept.demo.dto.response.CustomerResponseDto;
import com.monocept.demo.entity.Customer;
import com.monocept.demo.entity.User;
import com.monocept.demo.enums.Role;
import com.monocept.demo.exception.ResourceNotFoundException;
import com.monocept.demo.exception.ValidationException;
import com.monocept.demo.repository.CustomerRepository;
import com.monocept.demo.repository.UserRepository;
import com.monocept.demo.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	private final UserRepository userRepository;
	private final CustomerRepository customerRepository;

	private final ModelMapper modelMapper;

	// DTO -> Entity
	public Customer dtoToCustomer(CustomerRequestDto customerDTO) {
		return this.modelMapper.map(customerDTO, Customer.class);
	}

	// Entity -> DTO
	public CustomerResponseDto customerToDto(Customer customer) {
		CustomerResponseDto dto = new CustomerResponseDto();
		dto.setCustomerId(customer.getCustomerId());
		dto.setFullName(customer.getUser().getFullName());
		dto.setEmail(customer.getUser().getEmail());
		dto.setDateOfBirth(customer.getDateOfBirth());
		dto.setAddress(customer.getAddress());
		dto.setCity(customer.getCity());
		dto.setState(customer.getState());
		dto.setPinCode(customer.getPinCode());
		dto.setNomineeName(customer.getNomineeName());
		if (customer.getAgent() != null) {
			dto.setAgentId(customer.getAgent().getUserId());
			dto.setAgentName(customer.getAgent().getFullName());
		}
		return dto;
	}

	@Override
	public CustomerResponseDto createCustomer(Long userId, CustomerRequestDto customerRequestDto) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User Not found with this id: " + userId));

		User agent = userRepository.findById(customerRequestDto.getAgentId())
				.orElseThrow(() -> new ResourceNotFoundException("Agent not found"));

		if (agent.getRole() != Role.AGENT) {
			throw new RuntimeException("Selected user is not an Agent");
		}
		Customer customer = new Customer();
		if (customerRequestDto.getDateOfBirth() != null
				&& Period.between(customerRequestDto.getDateOfBirth(), LocalDate.now()).getYears() < 18) {

			throw new ValidationException("Customer must be at least 18 years old");
		}
		customer.setDateOfBirth(customerRequestDto.getDateOfBirth());

		customer.setAddress(customerRequestDto.getAddress());
		customer.setCity(customerRequestDto.getCity());
		customer.setState(customerRequestDto.getState());
		customer.setPinCode(customerRequestDto.getPinCode());
		customer.setNomineeName(customerRequestDto.getNomineeName());
		customer.setNomineeRelation(customerRequestDto.getNomineeRelation());

		customer.setUser(user);
		customer.setAgent(agent);

		Customer savedCustomer = customerRepository.save(customer);
		return customerToDto(savedCustomer);
	}

	@Override
	public List<CustomerResponseDto> getAllCustomers() {
		List<Customer> list = customerRepository.findAll();

		return list.stream().map(ctm -> customerToDto(ctm)).toList();
	}

	@Override
	public CustomerResponseDto getCustomerById(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

		return customerToDto(customer);
	}

	@Override
	public void deleteCustomer(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		customerRepository.delete(customer);
		log.info("Customer Deleted Successfully");
	}

	@Override
	public CustomerResponseDto updateCustomer(Long customerId, CustomerRequestDto customerRequestDto) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

		if (customerRequestDto.getDateOfBirth() != null
				&& Period.between(customerRequestDto.getDateOfBirth(), LocalDate.now()).getYears() < 18) {

			throw new ValidationException("Customer must be at least 18 years old");
		}
		customer.setDateOfBirth(customerRequestDto.getDateOfBirth());
		customer.setAddress(customerRequestDto.getAddress());
		customer.setCity(customerRequestDto.getCity());
		customer.setState(customerRequestDto.getState());
		customer.setPinCode(customerRequestDto.getPinCode());
		customer.setNomineeName(customerRequestDto.getNomineeName());
		customer.setNomineeRelation(customerRequestDto.getNomineeRelation());

		customerRepository.save(customer);

		return customerToDto(customer);
	}

	@Override
	public Page<CustomerResponseDto> getCustomersByAgent(Long agentId, Pageable pageable) {

		return customerRepository.findByAgentUserId(agentId, pageable).map(this::customerToDto);
	}

}
