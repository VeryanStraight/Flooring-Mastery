package com.veryan.FlooringMastery.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order implements model {
    public final LocalDate date;
    public final String customerName;
    public final Tax tax;
    public final Product product;
    public final double area;
    public final BigDecimal costPerSquareFoot;
    public final BigDecimal laborCostPerSquareFoot;
    public final BigDecimal materialCost;
    public final BigDecimal laborCostTax;
    public final BigDecimal taxTotal;
    public final BigDecimal total;

    public Order(LocalDate date, String customerName, Tax tax, Product product,
                 double area, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot, BigDecimal materialCost, BigDecimal laborCostTax, BigDecimal taxTotal, BigDecimal total) {
        this.date = date;
        this.customerName = customerName;
        this.tax = tax;
        this.product = product;
        this.area = area;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
        this.materialCost = materialCost;
        this.laborCostTax = laborCostTax;
        this.taxTotal = taxTotal;
        this.total = total;
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
                ", LaborCostTax=" + laborCostTax +
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
                "," + laborCostTax +
                "," + taxTotal +
                "," + total;
    }
}
