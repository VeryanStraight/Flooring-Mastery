package com.veryan.FlooringMastery.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order implements model {
    public final LocalDate date;
    public final int orderNumber;
    public final String customerName;
    public final State state;
    public final BigDecimal taxRate;
    public final ProductType productType;
    public final double area;
    public final BigDecimal costPerSquareFoot;
    public final BigDecimal laborCostPerSquareFoot;
    public final BigDecimal materialCost;
    public final BigDecimal laborCostTax;
    public final BigDecimal total;

    public Order(LocalDate date, int orderNumber, String customerName, State state, BigDecimal taxRate, ProductType productType, double area, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot, BigDecimal materialCost, BigDecimal laborCostTax, BigDecimal total) {
        this.date = date;
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.state = state;
        this.taxRate = taxRate;
        this.productType = productType;
        this.area = area;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
        this.materialCost = materialCost;
        this.laborCostTax = laborCostTax;
        this.total = total;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "Date=" + date +
                "OrderNumber=" + orderNumber +
                ", CustomerName='" + customerName + '\'' +
                ", State='" + state + '\'' +
                ", TaxRate=" + taxRate +
                ", ProductType='" + productType + '\'' +
                ", Area='" + area + '\'' +
                ", CostPerSquareFoot=" + costPerSquareFoot +
                ", LaborCostPerSquareFoot=" + laborCostPerSquareFoot +
                ", MaterialCost=" + materialCost +
                ", LaborCostTax=" + laborCostTax +
                ", Total=" + total +
                '}';
    }

    public String toCSV(){
        return orderNumber +
                ","+ customerName +
                "," + state +
                "," + taxRate +
                "," + productType +
                "," + area +
                "," + costPerSquareFoot +
                "," + laborCostPerSquareFoot +
                "," + materialCost +
                "," + laborCostTax +
                "," + total;
    }
}
