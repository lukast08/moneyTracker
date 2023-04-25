package com.example.moneyTracker.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

@Converter
public class MonetaryAmountConverter implements AttributeConverter<MonetaryAmount, String> {

    @Override
    public String convertToDatabaseColumn(MonetaryAmount monetaryAmount) {
        if (monetaryAmount == null) {
            return null;
        }
        return MonetaryFormats.getAmountFormat(Locale.getDefault()).format(monetaryAmount);
    }

    @Override
    public MonetaryAmount convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return MonetaryFormats.getAmountFormat(Locale.getDefault()).parse(dbData);
    }
}
