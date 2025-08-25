package com.henry.atm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.henry.atm.repository.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
	Optional<CustomerEntity> findByName(String name);
}
