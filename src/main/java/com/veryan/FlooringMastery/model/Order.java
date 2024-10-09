package com.veryan.FlooringMastery.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Order implements model {
    public final LocalDate date;
    public final String customerName;
    public final Tax tax;
    public final Product product;
    public final BigDecimal area;
    public final BigDecimal costPerSquareFoot;
    public final BigDecimal laborCostPerSquareFoot;
    public final BigDecimal materialCost;
    public final BigDecimal laborCost;
    public final BigDecimal taxTotal;
    public final BigDecimal total;

    public Order(LocalDate date, String customerName, Tax tax, Product product,
                 BigDecimal area, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot) {

        this.materialCost = costPerSquareFoot.multiply(area);
        this.laborCost = laborCostPerSquareFoot.multiply(area);
        this.taxTotal = materialCost.add(laborCost).multiply(tax.taxRate.divide(new BigDecimal(100), RoundingMode.HALF_UP));
        this.total = materialCost.add(laborCost).add(taxTotal);

        this.date = date;
        this.customerName = customerName;
        this.tax = tax;
        this.product = product;
        this.area = area;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "Date=" + date +
                ", CustomerName='" + customerName + '\'' +
                ", State='" + tax.state + '\'' +
                ", TaxRate=" + tax.taxRate +
                ", ProductType='" + product.productType + '\'' +
                ", Area='" + area + '\'' +
                ", CostPerSquareFoot=" + costPerSquareFoot +
                ", LaborCostPerSquareFoot=" + laborCostPerSquareFoot +
                ", MaterialCost=" + materialCost +
                ", LaborCostTax=" + laborCost +
                ", TaxTotal=" + taxTotal +
                ", Total=" + total +
                '}';
    }

    public String toCSV(){
        return customerName +
                "," + tax.state +
                "," + tax.taxRate +
                "," + product.productType +
                "," + area +
                "," + costPerSquareFoot +
                "," + laborCostPerSquareFoot +
                "," + materialCost +
                "," + laborCost +
                "," + taxTotal +
                "," + total;
    }
}
