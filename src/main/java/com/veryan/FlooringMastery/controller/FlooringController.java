package com.veryan.FlooringMastery.controller;

import com.veryan.FlooringMastery.model.Order;
import com.veryan.FlooringMastery.model.OrderID;
import com.veryan.FlooringMastery.serviceLayer.InvalidInput;
import com.veryan.FlooringMastery.serviceLayer.NoSuchOrder;
import com.veryan.FlooringMastery.serviceLayer.OrderAlreadyExists;
import com.veryan.FlooringMastery.serviceLayer.Service;
import com.veryan.FlooringMastery.veiw.Veiw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;


/**
 * the controller for the application
 * communicates with vew and service to run the program
 */
@Controller
public class FlooringController {
    private final Service service;
    private final Veiw veiw;
    private boolean running = true;
    private String methodRepeat = "";

    @Autowired
    public FlooringController(Service service, Veiw veiw) {
        this.service = service;
        this.veiw = veiw;
    }

    public void run() {
        while (running) {
            String action;
            if (methodRepeat.isEmpty()) {
                action = veiw.menu().toLowerCase();
            } else {
                action = methodRepeat;
                methodRepeat = "";
            }

            switch (action) {
                case "display":
                    display();
                    break;
                case "add":
                    add();
                    break;
                case "edit":
                    edit();
                    break;
                case "remove":
                    remove();
                    break;
                case "export":
                    veiw.userMessage("exported data");
                    service.exportData();
                    break;
                case "quit":
                    service.exportData();
                    running=false;
                    break;
            }
        }
    }

    private void display(){
        LocalDate date = veiw.askDisplayOrders();

        if(date == null){
            veiw.userMessage("invalid date format");
            return;
        }

        try {
            List<Order> orders = service.getOrders(date);
            veiw.displayOrders(orders);
        } catch (NoSuchOrder e) {
            veiw.userMessage("no orders");
        }
    }

    private void add(){
        Order order = veiw.makeOrder(service.getProducts());
        try {
            order = service.initliseOrder(order);
            veiw.displayOrder(order);
            boolean answer = veiw.userQuestion("add order? y/n");
            if(answer) {
                service.addOrder(order);
            }
        } catch (InvalidInput e) {
            veiw.userMessage("Invalid input, "+e.getMessage());
            this.methodRepeat = "add";
        } catch (OrderAlreadyExists e) {
            boolean answer = veiw.userQuestion("Order already exists, return to menu? y/n");
            if(!answer){this.methodRepeat="add";}
        }
    }
    private void edit(){
        OrderID orderID = veiw.findOrder();
        if(orderID.date() == null || orderID.orderNumber() < 0){
            veiw.userMessage("Invalid input");
            this.methodRepeat="edit";
            return;
        }

        Order order;
        try {
            order = service.getOrder(orderID.date(), orderID.orderNumber());
        } catch (NoSuchOrder e) {
            boolean answer = veiw.userQuestion("Cant find Order, return to menu? y/n");
            if(!answer){this.methodRepeat="edit";}
            return;
        }

        veiw.userMessage("Old Order");
        veiw.displayOrder(order);
        veiw.userMessage("input the changed data (cant change date)");
        Order newOrder = veiw.makeOrder(service.getProducts(), order.date);
        newOrder.orderNumber = order.orderNumber;

        try {
            newOrder = service.initliseOrder(newOrder);
            veiw.displayOrder(newOrder);
            boolean answer = veiw.userQuestion("replace order? y/n");
            if(answer) {
                service.replaceOrder(order, newOrder);
            }
        } catch (NoSuchOrder ignored) {
            //can't be reached
        } catch (InvalidInput e) {
            boolean answer = veiw.userQuestion("Invalid Input, "+e.getMessage()+" return to menu? y/n");
            if(!answer){this.methodRepeat="edit";}
        }
    }

    private void remove(){
        OrderID orderID = veiw.findOrder();
        Order order;
        try {
            order = service.getOrder(orderID.date(), orderID.orderNumber());
            veiw.displayOrder(order);
        } catch (NoSuchOrder e) {
            veiw.userMessage("no such order");
            return;
        }

        boolean answer = veiw.userQuestion("delete order? y/n");

        if(answer) {
            service.deleteOrder(order);
        }
    }
}
