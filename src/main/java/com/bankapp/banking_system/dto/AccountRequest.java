package com.bankapp.banking_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

// DTO for account creation request
public class AccountRequest {

    // Name cannot be empty
    @NotBlank(message = "Account holder name is required")
    private String accountHolderName;

    // Balance must be greater than 0
    @Positive(message = "Balance must be greater than 0")
    private Double balance;

    // Empty constructor
    public AccountRequest() {
    }

    // Constructor
    public AccountRequest(String accountHolderName, Double balance) {
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    // Getter
    public String getAccountHolderName() {
        return accountHolderName;
    }

    // Setter
    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    // Getter
    public Double getBalance() {
        return balance;
    }

    // Setter
    public void setBalance(Double balance) {
        this.balance = balance;
    }
}