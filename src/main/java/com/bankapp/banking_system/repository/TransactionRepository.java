package com.bankapp.banking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bankapp.banking_system.entity.Transaction;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountIdOrderByTimestampDesc(Long accountId);
}