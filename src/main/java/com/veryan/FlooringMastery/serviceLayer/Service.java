package com.veryan.FlooringMastery.serviceLayer;

import com.veryan.FlooringMastery.model.Order;
import com.veryan.FlooringMastery.model.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * the service interface, defines how the controller can interact with the service
 */
public interface Service {
    /**
     * checks the data has been loaded
     * @return weather the data has been loaded
     */
    boolean loadedData();
    /**
     * gets the orders belonging to a specific date
     * @param date the date
     * @return the list of orders
     * @throws NoSuchOrder if there are no order with that date
     */
    List<Order> getOrders(LocalDate date) throws NoSuchOrder;

    /**
     * gets all products
     * @return the set of products
     */
    Set<Product> getProducts();

    /**
     * creates a new order based on a temporary order
     * it validates the inputs and gets the correct tax and products before creating the new order
     * @param order the temporary order
     * @return the validated order
     * @throws InvalidInput if part of the given order is incorrect
     */
    Order initliseOrder(Order order) throws InvalidInput;

    /**
     * adds an order
     * @param order the valid order to add
     * @throws OrderAlreadyExists if the order already exists
     */
    void addOrder(Order order) throws OrderAlreadyExists;

    /**
     * gets an order based on its date and order number
     * @param date the date
     * @param orderNumber the order number
     * @return the found order
     * @throws NoSuchOrder if the order doesn't exist
     * @throws InvalidInput if the date or order number is invalid (null or negative)
     */
    Order getOrder(LocalDate date, int orderNumber) throws NoSuchOrder, InvalidInput;

    /**
     * replaces an order with another order
     * @param oldOrder the order to replace
     * @param newOrder the order to replace it with
     * @throws NoSuchOrder if the old order didn't exist
     * @throws InvalidInput if the new order doesnt share the same order number or date
     */
    void replaceOrder(Order oldOrder, Order newOrder) throws NoSuchOrder, InvalidInput;

    /**
     * to save data before closing
     */
    void exportData();

    /**
     * delete an order
     * does nothing if the order doesn't exist
     * @param order the order to delete
     */
    void deleteOrder(Order order);

}
