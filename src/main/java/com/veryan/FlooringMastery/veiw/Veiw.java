package com.veryan.FlooringMastery.veiw;

import com.veryan.FlooringMastery.model.Order;
import com.veryan.FlooringMastery.model.OrderID;
import com.veryan.FlooringMastery.model.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface Veiw {
        String menu();
        LocalDate askDisplayOrders();
        void displayOrders(List<Order> orders);
        Order makeOrder(Set<Product> products);
        Order makeOrder(Set<Product> products, LocalDate date);
        void displayOrder(Order order);
        void userMessage(String message);
        Boolean userQuestion(String question);
        OrderID findOrder();
}
