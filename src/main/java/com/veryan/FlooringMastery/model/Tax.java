package com.veryan.FlooringMastery.model;

import java.math.BigDecimal;

public class Tax implements model{
    public final String state;
    public final String stateName;
    public BigDecimal taxRate; //might want to make private because it can be changed

    public Tax(String state, String stateName, BigDecimal taxRate) {
        this.state = state;
        this.stateName = stateName;
        this.taxRate = taxRate;
    }

    @Override
    public String toCSV() {
        return state +
                "," + stateName +
                "," + taxRate;
    }

    @Override
    public String toString() {
        return "Tax{" +
                "state=" + state +
                ", stateName='" + stateName + '\'' +
                ", taxRate=" + taxRate +
                '}';
    }
}
