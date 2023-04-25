package com.example.moneyTracker.controller;

import com.example.moneyTracker.model.Account;
import com.example.moneyTracker.model.Transaction;
import com.example.moneyTracker.service.AccountService;
import com.example.moneyTracker.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.findAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return transactionService.findTransactionById(id)
                .map(transaction -> new ResponseEntity<>(transaction, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
//        CompletableFuture<Account> updatedAccountFuture = accountService.updateBalanceAsync(transaction.getAccount().getId(), transaction);
//        updatedAccountFuture.thenAccept(account -> {
//            if (account != null) {
//                transaction.setAccount(account);
//                transactionService.saveTransaction(transaction);
//            }
//        });

        accountService.updateBalance(transaction.getAccount().getId(), transaction);
//        transaction.setAccount(updatedAccount);
//        transactionService.saveTransaction(transaction);

        return new ResponseEntity<>(new Transaction(), HttpStatus.CREATED);
    }
}
