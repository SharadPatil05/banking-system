package com.bankapp.banking_system.repository;

import com.bankapp.banking_system.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}