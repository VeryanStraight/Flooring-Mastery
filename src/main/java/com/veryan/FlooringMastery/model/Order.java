package com.veryan.FlooringMastery.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

/**
 * the model that represents orders
 */
public class Order {
    /**the date*/
    public final LocalDate date;
    /**the order number*/
    public int orderNumber;
    /**customer name*/
    public final String customerName;
    /**the tax object*/
    public final Tax tax;
    /**the product object*/
    public final Product product;
    /**the area*/
    public final BigDecimal area;
    /**material cost*/
    public final BigDecimal materialCost;
    /**labour cost*/
    public final BigDecimal laborCost;
    /**total tax*/
    public final BigDecimal taxTotal;
    /**total cost*/
    public final BigDecimal total;

    /**
     * the order constructor. initialises the fields using the parameters and calculations
     * @param date the date
     * @param orderNumber the order number
     * @param customerName the customer name
     * @param tax the tax
     * @param product the product
     * @param area the area
     */
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

    /**
     * the order constructor without the order number. initialises the fields using the parameters and calculations
     * @param date the date
     * @param customerName the customer name
     * @param tax the tax
     * @param product the product
     * @param area the area
     */
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
}
