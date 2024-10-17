package com.veryan.FlooringMastery.veiw;

import com.veryan.FlooringMastery.model.Order;
import com.veryan.FlooringMastery.model.OrderID;
import com.veryan.FlooringMastery.model.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * the vew interface
 * it defines how the controller can interact with the view
 */
public interface Veiw {
        /**
         * shows the menu and waits for the user's choice
         * @return returns the chosen option
         * */
        String menu();

        /**
         * asks the user for a date to display orders for
         * @return the inputted date
         */
        LocalDate askDisplayOrders();

        /**
         * display the given orders
         * @param orders the orders to display
         */
        void displayOrders(List<Order> orders);

        /**
         * ask a user for the data to create an order
         * @param products all products (to give the user a preview)
         * @return the temporary order with the information given
         */
        Order makeOrder(Set<Product> products);

        /**
         * ask a user for order information for an order with a given date particular date
         * @param products the products to display to user
         * @param date the date to give the order
         * @return the temporary order with the information given
         */
        Order makeOrder(Set<Product> products, LocalDate date);

        /**
         * display one order
         * @param order the order to display
         */
        void displayOrder(Order order);

        /**
         * show the user a message
         * @param message the message to send to the user
         */
        void userMessage(String message);

        /**
         * ask the user a question
         * @param question the question to ask the user
         * @return the yes/no answer to the question
         */
        Boolean userQuestion(String question);

        /**
         * ask a user to a date and order number to find an order
         * @return an orderID that contains the order date and order number
         */
        OrderID findOrder();
}
