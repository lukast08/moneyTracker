package com.example.moneyTracker.model;

import com.example.moneyTracker.serialization.MonetaryAmountDeserializer;
import com.example.moneyTracker.serialization.MonetaryAmountSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.money.MonetaryAmount;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @JsonSerialize(using = MonetaryAmountSerializer.class)
    @JsonDeserialize(using = MonetaryAmountDeserializer.class)
    @Column(nullable = false)
    @Convert(converter = MonetaryAmountConverter.class)
    private MonetaryAmount balance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction) {
        transaction.setAccount(this);
        transactions.add(transaction);
        updateBalance(transaction);
    }

    private void updateBalance(Transaction transaction) {
        System.out.print("Balance before:");
        System.out.print(this.balance);
        System.out.println();
        if (transaction.getType().equalsIgnoreCase("INCOME")) {
            balance = balance.add(transaction.getAmount());
        } else if (transaction.getType().equalsIgnoreCase("EXPENSE")) {
            balance = balance.subtract(transaction.getAmount());
        }
        System.out.print("Balance after:");
        System.out.print(this.balance);
        System.out.println();
    }
}

