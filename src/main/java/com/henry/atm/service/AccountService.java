package com.henry.atm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.henry.atm.dto.AccountGetResponse;
import com.henry.atm.dto.CreateAccountRequest;
import com.henry.atm.dto.CreateAccountResponse;
import com.henry.atm.repository.AccountRepository;
import com.henry.atm.repository.CustomerRepository;
import com.henry.atm.repository.entity.AccountEntity;
import com.henry.atm.repository.entity.CustomerEntity;

@Service
public class AccountService {
	private final AccountRepository accountRepository;
	private final CustomerRepository customerRepository;

	public AccountService(final AccountRepository accountRepository, final CustomerRepository customerRepository) {
		this.accountRepository = accountRepository;
		this.customerRepository = customerRepository;
	}

	public CreateAccountResponse createAccount(final CreateAccountRequest request, final String customerId) {
		CustomerEntity customer = customerRepository.findById(customerId).orElseThrow();
		System.out.println("Found a customer for " + customerId);
		AccountEntity accountEntity = new AccountEntity();
		accountEntity.setCustomer(customer);
		accountEntity.setBalance(request.initialBalance() != null ? request.initialBalance() : 0.0);
		accountRepository.save(accountEntity);
		System.out.println("Created account");

		return new CreateAccountResponse(accountEntity.getId(), accountEntity.getBalance());
	}

	public AccountGetResponse getAccount(final String accountId) {
		Optional<AccountEntity> optionalAccount = accountRepository.findById(accountId);
		if (optionalAccount.isEmpty()) {
			return null; //throw exception, use exception mapper to map to 400
		}

		AccountEntity account = optionalAccount.get();
		if (!account.getCustomer().getId().equals(accountId)) {
			return null; //throw exception, use exception mapper to map to 401
		}

		return new AccountGetResponse(account.getId(), account.getBalance());
	}

	public List<AccountGetResponse> getAccounts(final String customerId) {
		return accountRepository.findByCustomerId(customerId)
				.stream()
				.map(acc -> new AccountGetResponse(acc.getId(), acc.getBalance()))
				.toList();
	}
}
