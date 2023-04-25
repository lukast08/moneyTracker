package com.example.moneyTracker.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import javax.money.MonetaryAmount;
import java.io.IOException;

public class MonetaryAmountSerializer extends JsonSerializer<MonetaryAmount> {
    @Override
    public void serialize(MonetaryAmount value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toString());
    }
}
