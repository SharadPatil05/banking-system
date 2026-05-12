package com.bankapp.banking_system.service;

import com.bankapp.banking_system.dto.TransactionResponse;
import java.util.stream.Collectors;
import com.bankapp.banking_system.entity.Account;
import com.bankapp.banking_system.entity.Transaction;
import com.bankapp.banking_system.enums.TransactionStatus;
import com.bankapp.banking_system.enums.TransactionType;
import com.bankapp.banking_system.exception.InsufficientBalanceException;
import com.bankapp.banking_system.repository.AccountRepository;
import com.bankapp.banking_system.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import com.bankapp.banking_system.dto.AccountResponse;
import com.bankapp.banking_system.mapper.AccountMapper;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository,
                          TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public AccountResponse getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElse(null);

    if (account == null) {
        return null;
}

return mapToResponse(account);
    }

    public Account deposit(Long id, Double amount) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        Transaction tx = new Transaction();
        tx.setAccountId(account.getId());
        tx.setType(TransactionType.DEPOSIT);
        tx.setAmount(amount);
        tx.setTimestamp(LocalDateTime.now());
        tx.setStatus(TransactionStatus.SUCCESS);

        transactionRepository.save(tx);

        return account;
    }

    public Account withdraw(Long id, Double amount) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient Balance");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        Transaction tx = new Transaction();
        tx.setAccountId(account.getId());
        tx.setType(TransactionType.WITHDRAW);
        tx.setAmount(amount);
        tx.setTimestamp(LocalDateTime.now());
        tx.setStatus(TransactionStatus.SUCCESS);

        transactionRepository.save(tx);

        return account;
    }

    
    public List<TransactionResponse> getTransactionHistory(Long accountId) {

        List<Transaction> transactions =
                transactionRepository.findByAccountIdOrderByTimestampDesc(accountId);

        return transactions.stream().map(transaction -> {

            TransactionResponse response = new TransactionResponse();

            response.setTransactionId(transaction.getTransactionId());

            response.setType(transaction.getType().name());

            response.setAmount(transaction.getAmount());

            response.setStatus(transaction.getStatus().name());

            response.setTimestamp(transaction.getTimestamp().toString());

            return response;

        }).collect(Collectors.toList());
    }
    
    public void transfer(Long fromId, Long toId, Double amount) {

        Account fromAccount = accountRepository.findById(fromId)
                .orElseThrow(() -> new RuntimeException("From account not found"));

        Account toAccount = accountRepository.findById(toId)
                .orElseThrow(() -> new RuntimeException("To account not found"));

        if (fromAccount.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient Balance");
        }

        // debit
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        accountRepository.save(fromAccount);

        Transaction debitTx = new Transaction();
        debitTx.setAccountId(fromAccount.getId());
        debitTx.setType(TransactionType.WITHDRAW);
        debitTx.setAmount(amount);
        debitTx.setTimestamp(LocalDateTime.now());
        debitTx.setStatus(TransactionStatus.SUCCESS);
        transactionRepository.save(debitTx);

        // credit
        toAccount.setBalance(toAccount.getBalance() + amount);
        accountRepository.save(toAccount);

        Transaction creditTx = new Transaction();
        creditTx.setAccountId(toAccount.getId());
        creditTx.setType(TransactionType.DEPOSIT);
        creditTx.setAmount(amount);
        creditTx.setTimestamp(LocalDateTime.now());
<<<<<<< HEAD
        creditTx.setStatus(TransactionStatus.SUCCESS);                     // Transfer validation pending
        transactionRepository.save(creditTx);                             // Main branch validation logic
=======
        creditTx.setStatus(TransactionStatus.SUCCESS);             // Added validation for transfer amount
        transactionRepository.save(creditTx);                             // Feature branch validation logic
>>>>>>> feature-transfer-api
    }

    private AccountResponse mapToResponse(Account account) {

        AccountResponse response = new AccountResponse();

        response.setId(account.getId());
        response.setAccountHolderName(account.getAccountHolderName());
        response.setBalance(account.getBalance());                        // Added transfer validation logic

        return response;
    }
}