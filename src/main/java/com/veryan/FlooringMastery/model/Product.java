package com.veryan.FlooringMastery.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product implements model{
    public final String productType;
    public BigDecimal costPerSquareFoot = new BigDecimal("0"); //maybe make these hidden
    public BigDecimal laborCostPerSquareFoot = new BigDecimal("0"); //maybe make these hidden

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productType, product.productType) && costPerSquareFoot.compareTo(product.costPerSquareFoot) == 0 && laborCostPerSquareFoot.compareTo(product.laborCostPerSquareFoot) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productType, costPerSquareFoot, laborCostPerSquareFoot);
    }

    public Product(String productType, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot) {
        this.productType = productType;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public Product(String productType){
        this.productType = productType;
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
