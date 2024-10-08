package com.veryan.FlooringMastery.dao;

import com.veryan.FlooringMastery.model.Order;
import com.veryan.FlooringMastery.model.Product;
import com.veryan.FlooringMastery.model.Tax;

import java.time.LocalDate;
import java.util.List;

public interface dao {
    void saveData();
    List<Order> getOrders();
    List<Tax> getTaxes();
    List<Product> getProducts();
    Order getOrder(LocalDate date, int orderNumber);
    void addOrder(Order order);
    void replaceOrder(Order Oldorder, Order newOrder);
    void removeOrder(Order order);
}
