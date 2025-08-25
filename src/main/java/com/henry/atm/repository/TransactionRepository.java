package com.henry.atm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.henry.atm.repository.entity.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {
	List<TransactionEntity> findByAccountIdOrderByTimestampDesc(String accountId);
}
