package com.veryan.FlooringMastery.serviceLayer;

import com.veryan.FlooringMastery.dao.FileException;
import com.veryan.FlooringMastery.model.Order;

import java.time.LocalDate;
import java.util.List;

public interface Service {
    List<Order> getOrders() throws NoSuchOrder, InvalidInput;
    void addOrder(Order order) throws InvalidInput, OrderAlreadyExists;

    void getOrder(LocalDate date, int orderNumber) throws NoSuchOrder, InvalidInput;
    void replaceOrder(Order oldOrder, Order newOrder) throws NoSuchOrder, InvalidInput;
    void exportData();

}
