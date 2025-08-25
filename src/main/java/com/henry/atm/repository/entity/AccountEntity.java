package com.henry.atm.repository.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class AccountEntity {
	@Id
	@Column(columnDefinition = "CHAR(36)")
	private String id = UUID.randomUUID().toString();

	@ManyToOne
	@JoinColumn(name = "fkCustomer", nullable = false)
	private CustomerEntity customer;

	private double balance = 0.0;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(final CustomerEntity customer) {
		this.customer = customer;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(final double balance) {
		this.balance = balance;
	}
}
