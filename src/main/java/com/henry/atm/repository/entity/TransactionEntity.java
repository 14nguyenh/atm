package com.henry.atm.repository.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.henry.atm.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction")
public class TransactionEntity {
	@Id
	@Column(columnDefinition = "CHAR(36)")
	private String id = UUID.randomUUID().toString();

	@ManyToOne
	@JoinColumn(name = "fkAccount", nullable = false)
	private AccountEntity account;

	@Enumerated(EnumType.STRING)
	private TransactionType type;

	private Double amount;

	private LocalDateTime timestamp = LocalDateTime.now();

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public AccountEntity getAccount() {
		return account;
	}

	public void setAccount(final AccountEntity account) {
		this.account = account;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(final TransactionType type) {
		this.type = type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
