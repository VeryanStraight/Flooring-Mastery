package com.veryan.FlooringMastery.serviceLayer;

import com.veryan.FlooringMastery.dao.FileException;
import com.veryan.FlooringMastery.model.Order;

import java.time.LocalDate;
import java.util.List;

public interface Service {
    List<Order> getOrders();
    void addOrder(Order order) throws InvalidInput, OrderAlreadyExists;

    Order getOrder(LocalDate date, int orderNumber) throws NoSuchOrder;
    void replaceOrder(Order oldOrder, Order newOrder) throws NoSuchOrder, InvalidInput;
    void exportData();

    void deleteOrder(Order order);

}
