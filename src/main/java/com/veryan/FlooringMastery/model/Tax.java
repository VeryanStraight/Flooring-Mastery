package com.veryan.FlooringMastery.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * the model for the tax
 */
public class Tax{
    /**the state code*/
    public final String state;
    /**the state name*/
    public final String stateName;
    /**the tax rate*/
    public BigDecimal taxRate = BigDecimal.ZERO; //might want to make private because it can be changed

    /**
     * the constructor for the tax
     * @param state the state
     * @param stateName the state name
     * @param taxRate the tax rate
     */
    public Tax(String state, String stateName, BigDecimal taxRate) {
        this.state = state;
        this.stateName = stateName;
        this.taxRate = taxRate;
    }

    /**
     * a constructor for a temporary tax object (when only state is known)
     * @param state the state
     */
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


    @Override
    public String toString() {
        return "Tax{" +
                "state=" + state +
                ", stateName='" + stateName + '\'' +
                ", taxRate=" + taxRate +
                '}';
    }
}
