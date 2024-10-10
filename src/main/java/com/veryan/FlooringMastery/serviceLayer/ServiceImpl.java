package com.veryan.FlooringMastery.serviceLayer;

import com.veryan.FlooringMastery.dao.FileException;
import com.veryan.FlooringMastery.dao.dao;
import com.veryan.FlooringMastery.model.Order;
import com.veryan.FlooringMastery.model.Product;
import com.veryan.FlooringMastery.model.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ServiceImpl implements com.veryan.FlooringMastery.serviceLayer.Service {
    private final dao dao;

    @Autowired
    public ServiceImpl(dao dataAccessObject) {
        this.dao = dataAccessObject;
    }

    @Override
    public List<Order> getOrders() {
        return dao.getOrders();
    }

    @Override
    public void addOrder(Order order) throws InvalidInput, OrderAlreadyExists {
        order = initliseOrder(order);

        if(dao.getOrders().contains(order)){
            throw new OrderAlreadyExists("already exists: "+order);
        }
        dao.addOrder(order);
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) throws NoSuchOrder {
        return dao.getOrder(date, orderNumber);
    }

    @Override
    public void replaceOrder(Order oldOrder, Order newOrder) throws NoSuchOrder, InvalidInput {
        if(!oldOrder.date.equals(newOrder.date)){
            throw new InvalidInput("cant replace orders with different dates or orderNumbers");
        }

        newOrder = initliseOrder(newOrder);
        dao.replaceOrder(oldOrder, newOrder);

    }

    private Order initliseOrder(Order order) throws InvalidInput {
        Map<String, Product> products = dao.getProducts();
        Map<String, Tax> taxes = dao.getTaxes();

        if(!products.containsKey(order.product.productType)){
            throw new InvalidInput("product doesn't exist");
        }
        Product p = products.get(order.product.productType);
        if(!taxes.containsKey(order.tax.state)){
            throw new InvalidInput("state doesn't exist");
        }
        Tax t = taxes.get(order.tax.state);

        return new Order(order.date,order.customerName, t,p,order.area);
    }

    @Override
    public void exportData() {
        try {
            dao.saveData();
        } catch (FileException e) {
            System.out.println("failed to save");
        }
    }

    @Override
    public void deleteOrder(Order order){
        dao.removeOrder(order);
    }
}
