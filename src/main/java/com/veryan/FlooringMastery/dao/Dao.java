package com.veryan.FlooringMastery.dao;

import com.veryan.FlooringMastery.model.Order;
import com.veryan.FlooringMastery.model.Product;
import com.veryan.FlooringMastery.model.Tax;
import com.veryan.FlooringMastery.serviceLayer.NoSuchOrder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * the dao interface
 * it defines how the service can interact with the data
 */
public interface Dao {
    /**
     * a method to ensure all data is saved before the program ends
     * @throws DaoException if something goes wrong with saving and closing
     */
    void saveAndClose() throws DaoException;

    /**
     * gets all orders
     * @return all orders
     */
    List<Order> getOrders();

    /**
     * get order with on a particular date
     * @param date the date
     * @return the order on the date
     */
    List<Order> getOrders(LocalDate date);

    /**
     * gets all taxes
     * @return a map of the state the corresponding tax
     */
    Map<String, Tax> getTaxes();

    /**
     * gets all products
     * @return a map of the productType the corresponding product
     */
    Map<String, Product> getProducts();

    /**
     * gets a particular order
     * @param date the order date
     * @param orderNumber the order number
     * @return the existing order
     * @throws NoSuchOrder if the order doesn't exist
     */
    Order getOrder(LocalDate date, int orderNumber) throws NoSuchOrder;

    /**
     * adds an order (should be a valid order)
     * @param order the order to add
     */
    void addOrder(Order order);

    /**
     * replaces an order with a new one with the same date and number
     * @param oldOrder the order to replace
     * @param newOrder the order to replace it with
     * @throws NoSuchOrder if the oldOrder doesn't exist
     */
    void replaceOrder(Order oldOrder, Order newOrder) throws NoSuchOrder;

    /**
     * remove a given order, it does nothing if the order doesn't exist
     * @param order the order to remove
     */
    void removeOrder(Order order);

    /**
     * checks if a particular order exists
     * @param order the order to check
     * @return weather or not it exists
     */
    boolean orderExists(Order order);
}
