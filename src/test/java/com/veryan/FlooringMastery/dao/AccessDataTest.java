package com.veryan.FlooringMastery.dao;

import com.veryan.FlooringMastery.model.Order;
import com.veryan.FlooringMastery.model.Product;
import com.veryan.FlooringMastery.model.Tax;
import com.veryan.FlooringMastery.serviceLayer.NoSuchOrder;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccessDataTest {
    @Test
    void testLoad() throws FileException, FileNotFoundException {
        new AccessData("src/test/resources");
    }

    @Test
    void testGetters() throws FileException, FileNotFoundException {
        AccessData accessData = new AccessData("src/test/resources");
        Map<String, Tax> taxes = accessData.getTaxes();
        assertEquals(taxes.size(), 4);
        List<Order> orders = accessData.getOrders();
        assertEquals(orders.size(), 3);
        Map<String, Product> products = accessData.getProducts();
        assertEquals(products.size(), 4);
    }

    @Test
    void testSaveData() throws FileException, FileNotFoundException {
        AccessData accessData = new AccessData("src/test/resources");
        accessData.saveData();
    }

    @Test
    void testGetOrder() throws FileException, NoSuchOrder {
        AccessData accessData = new AccessData("src/test/resources");
        accessData.getOrder(LocalDate.parse("2013-01-06"), 0);
    }

    @Test
    void testAddOrder() throws FileException {
        LocalDate date = LocalDate.of(2024, 7,2);
        String name = "";
        Tax tax = new Tax("", "", BigDecimal.ONE);
        Product product = new Product("", BigDecimal.ONE, BigDecimal.ONE);
        double area = 10;
        BigDecimal costPerSquareFoot = BigDecimal.ONE;
        BigDecimal laborCostPerSquareFoot = BigDecimal.ONE;
        BigDecimal materialCost = BigDecimal.ONE;
        BigDecimal laborCostTax = BigDecimal.ONE;
        BigDecimal taxTotal = BigDecimal.ONE;
        BigDecimal total = BigDecimal.ONE;

        Order order = new Order(date,name, tax,product,area,costPerSquareFoot,laborCostPerSquareFoot,materialCost,laborCostTax,taxTotal,total);
        AccessData accessData = new AccessData("src/test/resources");
        accessData.addOrder(order);
    }

    @Test
    void testReplaceOrder() throws FileException, NoSuchOrder {
        LocalDate date = LocalDate.of(2024, 7,2);
        String name = "";
        Tax tax = new Tax("", "", BigDecimal.ONE);
        Product product = new Product("", BigDecimal.ONE, BigDecimal.ONE);
        double area = 10;
        BigDecimal costPerSquareFoot = BigDecimal.ONE;
        BigDecimal laborCostPerSquareFoot = BigDecimal.ONE;
        BigDecimal materialCost = BigDecimal.ONE;
        BigDecimal laborCostTax = BigDecimal.ONE;
        BigDecimal taxTotal = BigDecimal.ONE;
        BigDecimal total = BigDecimal.ONE;

        Order order = new Order(date,name, tax,product,area,costPerSquareFoot,laborCostPerSquareFoot,materialCost,laborCostTax,taxTotal,total);

        Order order2 = new Order(date,"name", tax,product,area,costPerSquareFoot,laborCostPerSquareFoot,materialCost,laborCostTax,taxTotal,total);
        AccessData accessData = new AccessData("src/test/resources");
        accessData.addOrder(order);
        accessData.replaceOrder(order, order2);
    }

    @Test
    void testRemoveOrder() throws FileException {
        LocalDate date = LocalDate.of(2024, 7,2);
        String name = "";
        Tax tax = new Tax("", "", BigDecimal.ONE);
        Product product = new Product("", BigDecimal.ONE, BigDecimal.ONE);
        double area = 10;
        BigDecimal costPerSquareFoot = BigDecimal.ONE;
        BigDecimal laborCostPerSquareFoot = BigDecimal.ONE;
        BigDecimal materialCost = BigDecimal.ONE;
        BigDecimal laborCostTax = BigDecimal.ONE;
        BigDecimal taxTotal = BigDecimal.ONE;
        BigDecimal total = BigDecimal.ONE;

        Order order = new Order(date,name, tax,product,area,costPerSquareFoot,laborCostPerSquareFoot,materialCost,laborCostTax,taxTotal,total);
        AccessData accessData = new AccessData("src/test/resources");
        accessData.addOrder(order);
        accessData.removeOrder(order);
    }

    @Test
    void testOrderExists() throws FileException {
        LocalDate date = LocalDate.of(2024, 7,2);
        String name = "";
        Tax tax = new Tax("", "", BigDecimal.ONE);
        Product product = new Product("", BigDecimal.ONE, BigDecimal.ONE);
        double area = 10;
        BigDecimal costPerSquareFoot = BigDecimal.ONE;
        BigDecimal laborCostPerSquareFoot = BigDecimal.ONE;
        BigDecimal materialCost = BigDecimal.ONE;
        BigDecimal laborCostTax = BigDecimal.ONE;
        BigDecimal taxTotal = BigDecimal.ONE;
        BigDecimal total = BigDecimal.ONE;

        Order order = new Order(date,name, tax,product,area,costPerSquareFoot,laborCostPerSquareFoot,materialCost,laborCostTax,taxTotal,total);
        AccessData accessData = new AccessData("src/test/resources");
        accessData.addOrder(order);
        assertTrue(accessData.orderExists(order));
    }

}
