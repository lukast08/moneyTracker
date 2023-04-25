package com.example.moneyTracker.service;

import com.example.moneyTracker.model.Account;
import com.example.moneyTracker.model.Transaction;
import com.example.moneyTracker.repository.AccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class AccountService {
    private static final Logger logger = LogManager.getLogger(AccountService.class);
    @Autowired
    private AccountRepository accountRepository;

    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> findAccountById(Long id) {
        return accountRepository.findById(id);
    }

    public Account saveAccount(Account account) {
        logger.debug("Creating new account for user with ID: {}", account.getUser().getId());
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public Account updateBalance(Long accountId, Transaction newTransaction) {
        logger.debug("Updating balance for account with ID: {}", accountId);
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.addTransaction(newTransaction);
            return accountRepository.save(account);
        }
        return null;
    }

    public CompletableFuture<Account> updateBalanceAsync(Long accountId, Transaction newTransaction) {
        return CompletableFuture.supplyAsync(() -> {
            synchronized (this) {
                Optional<Account> optionalAccount = accountRepository.findById(accountId);
                if (optionalAccount.isPresent()) {
                    Account account = optionalAccount.get();
                    account.addTransaction(newTransaction);
                    return accountRepository.save(account);
                }
                return null;
            }
        });
    }
}
