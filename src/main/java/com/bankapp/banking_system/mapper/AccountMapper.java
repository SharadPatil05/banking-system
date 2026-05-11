package com.bankapp.banking_system.mapper;

import com.bankapp.banking_system.dto.AccountResponse;
import com.bankapp.banking_system.entity.Account;

public class AccountMapper {

    // Convert Entity -> DTO
    public static AccountResponse mapToResponse(Account account) {

        // Create DTO object
        AccountResponse response = new AccountResponse();

        // Copy data from entity to DTO
        response.setId(account.getId());

        response.setAccountHolderName(
                account.getAccountHolderName()
        );

        response.setBalance(account.getBalance());

        // Return clean response
        return response;
    }
}