package com.henry.atm.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.henry.atm.dto.LoginPostRequest;
import com.henry.atm.dto.LoginPostResponse;
import com.henry.atm.dto.RegisterPostRequest;
import com.henry.atm.dto.RegisterPostResponse;
import com.henry.atm.repository.CustomerRepository;
import com.henry.atm.repository.entity.CustomerEntity;

@Service
public class CustomerService {
	private final CustomerRepository customerRepository;
	private final AuthService authService;
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public CustomerService(final CustomerRepository customerRepository, final AuthService authService) {
		this.customerRepository = customerRepository;
		this.authService = authService;
	}

	public RegisterPostResponse register(final RegisterPostRequest registerRequest) {
		String pinHash = passwordEncoder.encode(registerRequest.pin());
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setName(registerRequest.name());
		customerEntity.setPinHash(pinHash);
		customerRepository.save(customerEntity);

		String token = authService.generateToken(customerEntity.getId());
		return new RegisterPostResponse(customerEntity.getId(), token);
	}

	public LoginPostResponse login(final LoginPostRequest loginRequest) {
		Optional<CustomerEntity> optionalCustomer = customerRepository.findByName(loginRequest.name());
		if (optionalCustomer.isEmpty()) {
			return null; //turn into a runtime exception and map it to a HTTP response code in an exception mapper
		}

		CustomerEntity customerEntity = optionalCustomer.get();
		if (!passwordEncoder.matches(loginRequest.pin(), customerEntity.getPinHash())) {
			return null; //turn into a runtime exception and map it to a HTTP response code in an exception mapper
		}

		return new LoginPostResponse(authService.generateToken(customerEntity.getId()));

	}
}
