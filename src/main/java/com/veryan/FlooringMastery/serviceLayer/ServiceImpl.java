package com.veryan.FlooringMastery.serviceLayer;

import com.veryan.FlooringMastery.model.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ServiceImpl implements com.veryan.FlooringMastery.serviceLayer.Service {

    @Override
    public List<Order> getOrders() throws NoSuchOrder, InvalidInput {
        return null;
    }

    @Override
    public void addOrder() throws InvalidInput, OrderAlreadyExists {

    }

    @Override
    public void getOrder(LocalDate date, int orderNumber) throws NoSuchOrder, InvalidInput {

    }

    @Override
    public void replaceOrder(Order oldOrder, Order newOrder) {

    }

    @Override
    public void exportData() {

    }
}
