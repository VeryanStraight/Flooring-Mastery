package com.veryan.FlooringMastery.serviceLayer;

import com.veryan.FlooringMastery.dao.DaoException;
import com.veryan.FlooringMastery.dao.Dao;
import com.veryan.FlooringMastery.model.Order;
import com.veryan.FlooringMastery.model.Product;
import com.veryan.FlooringMastery.model.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ServiceImpl implements com.veryan.FlooringMastery.serviceLayer.Service {
    private final Dao dao;

    @Autowired
    public ServiceImpl(Dao dataAccessObject) {
        this.dao = dataAccessObject;
    }

    @Override
    public List<Order> getOrders(LocalDate date) throws NoSuchOrder{
        List<Order> li = dao.getOrders(date);
        if(li.isEmpty()){throw new NoSuchOrder();}

        return li;
    }

    @Override
    public Set<Product> getProducts() {
        return new HashSet<>(dao.getProducts().values());
    }


    @Override
    public void addOrder(Order order) throws OrderAlreadyExists {
        if(dao.orderExists(order)){
            throw new OrderAlreadyExists("already exists: "+order);
        }
        dao.addOrder(order);
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) throws NoSuchOrder {
        if (date == null || orderNumber < 0){throw new NoSuchOrder("invalid date or orderNumber");}
        return dao.getOrder(date, orderNumber);
    }

    @Override
    public void replaceOrder(Order oldOrder, Order newOrder) throws NoSuchOrder, InvalidInput {
        if(!oldOrder.date.equals(newOrder.date)){
            throw new InvalidInput("cant replace orders with different dates or orderNumbers");
        }

        dao.replaceOrder(oldOrder, newOrder);

    }

    public Order initliseOrder(Order order) throws InvalidInput {
        if(order.date == null || order.date.isBefore(LocalDate.now())){
            throw new InvalidInput("invalid date");
        }else if(order.area.doubleValue() < 100){
            throw new InvalidInput("area must be >= 100");
        }

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
        } catch (DaoException e) {
            System.out.println("failed to save");
        }
    }

    @Override
    public void deleteOrder(Order order){
        dao.removeOrder(order);
    }
}
