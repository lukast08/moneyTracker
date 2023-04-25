package com.example.moneyTracker.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.io.IOException;

public class MonetaryAmountDeserializer extends JsonDeserializer<MonetaryAmount> {
    @Override
    public MonetaryAmount deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return Monetary.getDefaultAmountFactory()
                .setCurrency(Monetary.getCurrency("USD"))
                .setNumber(p.getNumberValue())
                .create();
    }
}
