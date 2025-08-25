package com.henry.atm.repository.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class CustomerEntity {
	@Id
	@Column(columnDefinition = "CHAR(36)")
	private String id = UUID.randomUUID().toString();

	private String name;

	@Column(length = 60, nullable = false)
	private String pinHash;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getPinHash() {
		return pinHash;
	}

	public void setPinHash(final String pinHash) {
		this.pinHash = pinHash;
	}
}
