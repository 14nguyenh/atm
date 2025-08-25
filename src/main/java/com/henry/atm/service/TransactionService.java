package com.henry.atm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henry.atm.dto.DepositPostResponse;
import com.henry.atm.dto.TransactionGetResponse;
import com.henry.atm.dto.WithdrawPostResponse;
import com.henry.atm.enums.TransactionType;
import com.henry.atm.repository.AccountRepository;
import com.henry.atm.repository.TransactionRepository;
import com.henry.atm.repository.entity.AccountEntity;
import com.henry.atm.repository.entity.TransactionEntity;

@Service
public class TransactionService {
	private final AccountRepository accountRepository;
	private final TransactionRepository transactionRepository;

	public TransactionService(final AccountRepository accountRepository, final TransactionRepository transactionRepository) {
		this.accountRepository = accountRepository;
		this.transactionRepository = transactionRepository;
	}

	@Transactional
	public DepositPostResponse deposit(final String accountId, final String customerId, final double amount) {
		Optional<AccountEntity> optionalAccount = accountRepository.findById(accountId);
		if (optionalAccount.isEmpty()) {
			return null;
		}
		System.out.println("found account");

		AccountEntity account = optionalAccount.get();
		if (!account.getCustomer().getId().equals(customerId)) {
			return null;
		}

		account.setBalance(account.getBalance() + amount);

		TransactionEntity transaction = new TransactionEntity();
		transaction.setAccount(account);
		transaction.setAmount(amount);
		transaction.setType(TransactionType.DEPOSIT);

		accountRepository.save(account);
		transactionRepository.save(transaction);
		return new DepositPostResponse(transaction.getId(), account.getId(), TransactionType.DEPOSIT.toString(), transaction.getAmount());
	}

	@Transactional
	public WithdrawPostResponse withdraw(final String accountId, final String customerId, final double amount) {
		Optional<AccountEntity> optionalAccount = accountRepository.findById(accountId);
		if (optionalAccount.isEmpty()) {
			return null;
		}

		AccountEntity account = optionalAccount.get();
		if (!account.getCustomer().getId().equals(customerId)) {
			return null;
		}

		account.setBalance(account.getBalance() - amount);

		TransactionEntity transaction = new TransactionEntity();
		transaction.setAccount(account);
		transaction.setAmount(amount);
		transaction.setType(TransactionType.WITHDRAWAL);

		accountRepository.save(account);
		transactionRepository.save(transaction);
		return new WithdrawPostResponse(transaction.getId(), account.getId(), TransactionType.WITHDRAWAL.toString(), transaction.getAmount());
	}

	public List<TransactionGetResponse> listTransactions(final String accountId, final String customerId) {
		Optional<AccountEntity> optionalAccount = accountRepository.findById(accountId);
		if (optionalAccount.isEmpty()) {
			return null;
		}

		AccountEntity account = optionalAccount.get();
		if (!account.getCustomer().getId().equals(customerId)) {
			return null;
		}

		List<TransactionEntity> transactions = transactionRepository.findByAccountIdOrderByTimestampDesc(accountId);
		return transactions.stream()
				.map(tx -> new TransactionGetResponse(
						tx.getId(),
						tx.getAccount().getId(),
						tx.getType().toString(),
						tx.getAmount(),
						tx.getTimestamp()))
				.toList();
	}
}
