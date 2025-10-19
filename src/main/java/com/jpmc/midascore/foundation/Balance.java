package com.jpmc.midascore.foundation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Balance {
    private double amount;

    // Default constructor for Jackson
    public Balance() {}

    @JsonCreator
    public Balance(@JsonProperty("amount") double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Balance{" + "amount=" + amount + '}';
    }
}
