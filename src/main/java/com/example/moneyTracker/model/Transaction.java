package com.example.moneyTracker.model;

import com.example.moneyTracker.serialization.MonetaryAmountDeserializer;
import com.example.moneyTracker.serialization.MonetaryAmountSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.money.MonetaryAmount;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonSerialize(using = MonetaryAmountSerializer.class)
    @JsonDeserialize(using = MonetaryAmountDeserializer.class)
    @Convert(converter = MonetaryAmountConverter.class)
    @Column(nullable = false)
    private MonetaryAmount amount;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String type; // "INCOME" or "EXPENSE"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}
