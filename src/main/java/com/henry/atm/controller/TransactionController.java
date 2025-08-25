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

import com.henry.atm.dto.DepositPostRequest;
import com.henry.atm.dto.DepositPostResponse;
import com.henry.atm.dto.TransactionGetResponse;
import com.henry.atm.dto.WithdrawPostRequest;
import com.henry.atm.dto.WithdrawPostResponse;
import com.henry.atm.service.TransactionService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
	private final TransactionService transactionService;

	public TransactionController(final TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping("/deposit/{accountId}")
	public ResponseEntity<DepositPostResponse> deposit(@PathVariable final String accountId,
			@RequestBody final DepositPostRequest depositPostRequest, final HttpServletRequest httpRequest) {
		System.out.println("Checking custId");
		String customerId = (String) httpRequest.getAttribute("customerId");
		if (customerId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		System.out.println("attempting to deposit");
		DepositPostResponse response = transactionService.deposit(accountId, customerId, depositPostRequest.amount());
		if (response == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return ResponseEntity.ok(response);
	}

	@PostMapping("/withdraw/{accountId}")
	public ResponseEntity<WithdrawPostResponse> withdraw(@PathVariable final String accountId,
			@RequestBody final WithdrawPostRequest withdrawPostRequest, final HttpServletRequest httpRequest) {
		String customerId = (String) httpRequest.getAttribute("customerId");
		if (customerId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		WithdrawPostResponse response = transactionService.withdraw(accountId, customerId, withdrawPostRequest.amount());
		if (response == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{accountId}")
	public ResponseEntity<List<TransactionGetResponse>> listTransactions(@PathVariable final String accountId,
			HttpServletRequest httpRequest) {
		String customerId = (String) httpRequest.getAttribute("customerId");
		if (customerId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		List<TransactionGetResponse> transactions = transactionService.listTransactions(accountId, customerId);
		if (transactions == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return ResponseEntity.ok(transactions);
	}
}
