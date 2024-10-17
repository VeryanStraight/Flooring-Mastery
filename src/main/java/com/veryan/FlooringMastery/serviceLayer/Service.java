package com.veryan.FlooringMastery.serviceLayer;

import com.veryan.FlooringMastery.model.Order;
import com.veryan.FlooringMastery.model.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface Service {
    List<Order> getOrders(LocalDate date) throws NoSuchOrder;

    Set<Product> getProducts();

    Order initliseOrder(Order order) throws InvalidInput;
    void addOrder(Order order) throws OrderAlreadyExists;

    Order getOrder(LocalDate date, int orderNumber) throws NoSuchOrder;
    void replaceOrder(Order oldOrder, Order newOrder) throws NoSuchOrder, InvalidInput;
    void exportData();

    void deleteOrder(Order order);

}
