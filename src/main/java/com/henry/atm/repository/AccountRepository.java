package com.henry.atm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.henry.atm.repository.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, String> {
	List<AccountEntity> findByCustomerId(String customerId);
}
