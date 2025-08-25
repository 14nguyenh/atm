package com.henry.atm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.henry.atm.dto.LoginPostRequest;
import com.henry.atm.dto.LoginPostResponse;
import com.henry.atm.dto.RegisterPostRequest;
import com.henry.atm.dto.RegisterPostResponse;
import com.henry.atm.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(final CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping("/register")
	public ResponseEntity<RegisterPostResponse> register(@RequestBody final RegisterPostRequest request) {
		System.out.println("CustomerController: received registration request");
		RegisterPostResponse response = customerService.register(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginPostResponse> login(@RequestBody final LoginPostRequest request) {
		LoginPostResponse response = customerService.login(request);
		if (response == null) {
			return ResponseEntity.status(401).build();
		}
		return ResponseEntity.ok(response);
	}
}
