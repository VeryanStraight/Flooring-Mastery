package com.veryan.FlooringMastery.dao;

import com.veryan.FlooringMastery.model.Order;
import com.veryan.FlooringMastery.model.Product;
import com.veryan.FlooringMastery.model.Tax;
import com.veryan.FlooringMastery.serviceLayer.NoSuchOrder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface Dao {
    void saveData() throws FileException;
    List<Order> getOrders();
    Map<String, Tax> getTaxes();
    Map<String, Product> getProducts();
    Order getOrder(LocalDate date, int orderNumber) throws NoSuchOrder ;
    void addOrder(Order order);
    void replaceOrder(Order Oldorder, Order newOrder) throws NoSuchOrder;
    void removeOrder(Order order);

    boolean orderExists(Order order);
}
