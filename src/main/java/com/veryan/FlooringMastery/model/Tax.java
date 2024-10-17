package com.veryan.FlooringMastery.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Tax implements model{
    public final String state;
    public final String stateName;
    public BigDecimal taxRate = BigDecimal.ZERO; //might want to make private because it can be changed

    public Tax(String state){
        this.state = state;
        this.stateName = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tax tax = (Tax) o;
        return Objects.equals(state, tax.state) && Objects.equals(stateName, tax.stateName) && taxRate.compareTo(tax.taxRate) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, stateName, taxRate);
    }

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
