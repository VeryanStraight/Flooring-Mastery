package com.veryan.FlooringMastery.dao;

import com.veryan.FlooringMastery.model.Order;
import com.veryan.FlooringMastery.model.Product;
import com.veryan.FlooringMastery.model.Tax;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccessData implements dao{
    ArrayList<Order> orders = new ArrayList<>();
    ArrayList<Tax> taxes = new ArrayList<>();
    ArrayList<Product> products = new ArrayList<>();

    private void loadData(){}
    @Override
    public void saveData(){}

    @Override
    public List<Order> getOrders() {
        return null;
    }

    @Override
    public List<Tax> getTaxes() {
        return null;
    }

    @Override
    public List<Product> getProducts() {
        return null;
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) {
        return null;
    }

    @Override
    public void addOrder(Order order) {

    }

    @Override
    public void replaceOrder(Order Oldorder, Order newOrder) {

    }

    @Override
    public void removeOrder(Order order) {

    }
}
