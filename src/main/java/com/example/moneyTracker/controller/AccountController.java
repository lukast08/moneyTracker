package com.example.moneyTracker.controller;

import com.example.moneyTracker.model.Account;
import com.example.moneyTracker.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.findAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return accountService.findAccountById(id)
                .map(account -> new ResponseEntity<>(account, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        System.out.println(account);
        Account newAccount = accountService.saveAccount(account);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        return accountService.findAccountById(id)
                .map(existingAccount -> {
                    account.setId(id);
                    Account updatedAccount = accountService.saveAccount(account);
                    return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.findAccountById(id)
                .ifPresent(account -> accountService.deleteAccount(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
