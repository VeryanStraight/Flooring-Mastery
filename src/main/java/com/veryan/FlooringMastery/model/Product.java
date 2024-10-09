package com.veryan.FlooringMastery.model;

import java.math.BigDecimal;

public class Product implements model{
    public final String productType;
    public BigDecimal costPerSquareFoot; //maybe make these hidden
    public BigDecimal laborCostPerSquareFoot; //maybe make these hidden

    public Product(String productType, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot) {
        this.productType = productType;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    @Override
    public String toString() {
        return "Products{" +
                "productType=" + productType +
                ", CostPerSquareFoot=" + costPerSquareFoot +
                ", LaborCostPerSquareFoot=" + laborCostPerSquareFoot +
                '}';
    }

    @Override
    public String toCSV() {
        return productType + "," + costPerSquareFoot + "," + laborCostPerSquareFoot;
    }
}
