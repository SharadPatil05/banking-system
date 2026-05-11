package com.bankapp.banking_system.controller;

import com.bankapp.banking_system.dto.AccountResponse;
import com.bankapp.banking_system.dto.ApiResponse;
import com.bankapp.banking_system.dto.TransactionResponse;
import com.bankapp.banking_system.entity.Account;
import com.bankapp.banking_system.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bankapp.banking_system.dto.AccountRequest;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    // Inject service layer
    @Autowired
    private AccountService accountService;

    @PostMapping            // @Valid = Check validation rules before entering method
public Account createAccount(@Valid @RequestBody AccountRequest request) { 

    // Create new Account entity object
    Account account = new Account();

    // Set account holder name from request DTO
    account.setAccountHolderName(request.getAccountHolderName());

    // Set balance from request DTO
    account.setBalance(request.getBalance());

    // Save account
    return accountService.createAccount(account);
}

    // Get all accounts
    @GetMapping
    public List<Account> getAllAccounts() {

        // Fetch all accounts
        return accountService.getAllAccounts();
    }

    // Get account by ID
    @GetMapping("/{id}")
    public ApiResponse<AccountResponse> getAccountById(@PathVariable Long id) {

        // Call service layer
        AccountResponse response = accountService.getAccountById(id);

        // Return wrapped response
        return new ApiResponse<>(
                true,
                "Account fetched successfully",
                response
        );
    }

    // Deposit money
    @PutMapping("/{id}/deposit")
    public Account deposit(@PathVariable Long id,
                           @RequestParam Double amount) {

        // Deposit amount
        return accountService.deposit(id, amount);
    }

    // Withdraw money
    @PutMapping("/{id}/withdraw")
    public Account withdraw(@PathVariable Long id,
                            @RequestParam Double amount) {

        // Withdraw amount
        return accountService.withdraw(id, amount);
    }

    // Transfer money
    @PutMapping("/transfer")
    public String transfer(@RequestParam Long fromId,
                           @RequestParam Long toId,
                           @RequestParam Double amount) {

        // Call transfer logic
        accountService.transfer(fromId, toId, amount);

        return "Transfer Successful";
    }

    // Get transaction history
    @GetMapping("/{id}/transactions")
    public List<TransactionResponse> getTransactions(@PathVariable Long id) {

        // Fetch transaction history
        return accountService.getTransactionHistory(id);
    }
}