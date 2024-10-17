package com.veryan.FlooringMastery.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

public class Order implements model {
    public final LocalDate date;
    public int orderNumber;
    public final String customerName;
    public final Tax tax;
    public final Product product;
    public final BigDecimal area;
    public final BigDecimal materialCost;
    public final BigDecimal laborCost;
    public final BigDecimal taxTotal;
    public final BigDecimal total;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return date.equals(order.date) && customerName.equals(order.customerName) && tax.equals(order.tax) && product.equals(order.product) && area.compareTo(order.area) == 0 && materialCost.compareTo(order.materialCost) == 0 && laborCost.compareTo(order.laborCost) == 0 && taxTotal.compareTo(order.taxTotal) == 0 && total.compareTo(order.total) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, customerName, tax, product, area, materialCost, laborCost, taxTotal, total);
    }

    public Order(LocalDate date, int orderNumber, String customerName, Tax tax, Product product,
                 BigDecimal area) {
        this.orderNumber = orderNumber;
        this.materialCost = product.costPerSquareFoot.multiply(area);
        this.laborCost = product.laborCostPerSquareFoot.multiply(area);
        this.taxTotal = materialCost.add(laborCost).multiply(tax.taxRate.divide(new BigDecimal(100), RoundingMode.HALF_UP));
        this.total = materialCost.add(laborCost).add(taxTotal);

        this.date = date;
        this.customerName = customerName;
        this.tax = tax;
        this.product = product;
        this.area = area;
    }

    public Order(LocalDate date, String customerName, Tax tax, Product product,
                 BigDecimal area) {
        this.materialCost = product.costPerSquareFoot.multiply(area);
        this.laborCost = product.laborCostPerSquareFoot.multiply(area);
        this.taxTotal = materialCost.add(laborCost).multiply(tax.taxRate.divide(new BigDecimal(100), RoundingMode.HALF_UP));
        this.total = materialCost.add(laborCost).add(taxTotal);

        this.date = date;
        this.customerName = customerName;
        this.tax = tax;
        this.product = product;
        this.area = area;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "Date=" + date +
                "OrderNumber=" + orderNumber +
                ", CustomerName='" + customerName + '\'' +
                ", State='" + tax.state + '\'' +
                ", TaxRate=" + tax.taxRate +
                ", ProductType='" + product.productType + '\'' +
                ", Area='" + area + '\'' +
                ", CostPerSquareFoot=" + product.costPerSquareFoot +
                ", LaborCostPerSquareFoot=" + product.laborCostPerSquareFoot +
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
                "," + product.costPerSquareFoot +
                "," + product.laborCostPerSquareFoot +
                "," + materialCost +
                "," + laborCost +
                "," + taxTotal +
                "," + total;
    }
}
