package com.example.moneyTracker.service;

import com.example.moneyTracker.model.Account;
import com.example.moneyTracker.model.Transaction;
import com.example.moneyTracker.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> findAccountById(Long id) {
        return accountRepository.findById(id);
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public Account updateBalance(Long accountId, Transaction newTransaction) {
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
