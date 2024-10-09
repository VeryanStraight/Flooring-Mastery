package com.veryan.FlooringMastery.veiw;

import com.veryan.FlooringMastery.model.Order;
import com.veryan.FlooringMastery.model.OrderID;

import java.util.List;

public interface Veiw {
        String menu();
        void displayOrders(List<Order> orders);
        Order makeOrder();
        void displayOrder(Order order);
        void userMessage(String message);
        Boolean userQuestion(String question);
        OrderID findOrder();
}
