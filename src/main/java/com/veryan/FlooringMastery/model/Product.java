package com.veryan.FlooringMastery.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * the model for the products
 */
public class Product{
    /**the product type*/
    public final String productType;
    /**the cost per square foot of the material*/
    public final BigDecimal costPerSquareFoot;
    /**the labour cost per square foot for installation*/
    public final BigDecimal laborCostPerSquareFoot ;

    /**
     * constructor for the product
     * @param productType the product type
     * @param costPerSquareFoot the cost per square foot
     * @param laborCostPerSquareFoot the labour cost per square foot
     */
    public Product(String productType, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot) {
        this.productType = productType;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    /**
     * constructor for a temporary product (when only product type known)
     * @param productType the product type
     */
    public Product(String productType){
        this.productType = productType;
        this.costPerSquareFoot = new BigDecimal("0");
        this.laborCostPerSquareFoot = new BigDecimal("0");
    }

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

    @Override
    public String toString() {
        return "Products{" +
                "productType=" + productType +
                ", CostPerSquareFoot=" + costPerSquareFoot +
                ", LaborCostPerSquareFoot=" + laborCostPerSquareFoot +
                '}';
    }
}
