package com.henry.atm.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.henry.atm.dto.AccountGetResponse;
import com.henry.atm.dto.CreateAccountRequest;
import com.henry.atm.dto.CreateAccountResponse;
import com.henry.atm.service.AccountService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/account")
public class AccountController {
	private final AccountService accountService;

	public AccountController(final AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping
	public ResponseEntity<CreateAccountResponse> createAccount(@RequestBody final CreateAccountRequest request,
			final HttpServletRequest httpRequest) {
		//turn this whole segment into an annotation that we can use to annotate each endpoint that needs authorization
		System.out.println("Checking custId");
		String customerId = (String) httpRequest.getAttribute("customerId");
		if (customerId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		System.out.println("Creating account");

		return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(request, customerId));
	}

	@GetMapping("/{accountId}")
	public ResponseEntity<AccountGetResponse> getAccount(@PathVariable final String accountId, HttpServletRequest httpRequest) {
		//turn this whole segment into an annotation that we can use to annotate each endpoint that needs authorization
		String customerId = (String) httpRequest.getAttribute("customerId");
		if (customerId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return ResponseEntity.ok(accountService.getAccount(accountId));
	}

	@GetMapping
	public ResponseEntity<List<AccountGetResponse>> listAccounts(HttpServletRequest httpRequest) {
		//turn this whole segment into an annotation that we can use to annotate each endpoint that needs authorization
		String customerId = (String) httpRequest.getAttribute("customerId");
		if (customerId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return ResponseEntity.ok(accountService.getAccounts(customerId));
	}
}
