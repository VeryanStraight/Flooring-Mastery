package com.veryan.FlooringMastery.serviceLayer;

import com.veryan.FlooringMastery.dao.FileException;
import com.veryan.FlooringMastery.dao.dao;
import com.veryan.FlooringMastery.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
        if(dao.getOrders().contains(order)){
            throw new OrderAlreadyExists("already exists: "+order);
        }
        dao.addOrder(order);
    }

    @Override
    public void getOrder(LocalDate date, int orderNumber) throws NoSuchOrder, InvalidInput {
        dao.getOrder(date, orderNumber);

    }

    @Override
    public void replaceOrder(Order oldOrder, Order newOrder) throws NoSuchOrder, InvalidInput {
        if(!oldOrder.date.equals(newOrder.date)){
            throw new InvalidInput("cant replace orders with different dates or orderNumbers");
        }
        dao.replaceOrder(oldOrder, newOrder);

    }

    @Override
    public void exportData() {
        try {
            dao.saveData();
        } catch (FileException e) {
            System.out.println("failed to save");
        }
    }
}
