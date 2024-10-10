package com.veryan.FlooringMastery.controller;

import com.veryan.FlooringMastery.model.Order;
import com.veryan.FlooringMastery.model.OrderID;
import com.veryan.FlooringMastery.serviceLayer.InvalidInput;
import com.veryan.FlooringMastery.serviceLayer.NoSuchOrder;
import com.veryan.FlooringMastery.serviceLayer.OrderAlreadyExists;
import com.veryan.FlooringMastery.serviceLayer.ServiceImpl;
import com.veryan.FlooringMastery.veiw.ConsoleVeiw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class FlooringController {
    private final ServiceImpl service;
    private final ConsoleVeiw consoleVeiw;
    private boolean running = true;
    private String methodRepeat = "";

    @Autowired
    public FlooringController(ServiceImpl service, ConsoleVeiw consoleVeiw) {
        this.service = service;
        this.consoleVeiw = consoleVeiw;
    }

    public void run() {
        while (running) {
            String action;
            if (methodRepeat.isEmpty()) {
                action = consoleVeiw.menu().toLowerCase();
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
                    consoleVeiw.userMessage("exported data");
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
        List<Order> orders = service.getOrders();
        consoleVeiw.displayOrders(orders);
    }

    private void add(){
        Order order = consoleVeiw.makeOrder();
        try {
            service.addOrder(order);
        } catch (InvalidInput e) {
            consoleVeiw.userMessage("Invalid input, tax or product is incorrect");
            this.methodRepeat = "add";
        } catch (OrderAlreadyExists e) {
            boolean answer = consoleVeiw.userQuestion("Order already exists, return to menu? y/n");
            if(!answer){this.methodRepeat="add";}
        }
    }
    private void edit(){
        OrderID orderID = consoleVeiw.findOrder();
        Order order;
        try {
            order = service.getOrder(orderID.date(), orderID.orderNumber());
        } catch (NoSuchOrder e) {
            boolean answer = consoleVeiw.userQuestion("Cant find Order, return to menu? y/n");
            if(!answer){this.methodRepeat="edit";}
            return;
        }

        consoleVeiw.userMessage("Old Order");
        consoleVeiw.displayOrder(order);
        consoleVeiw.userMessage("input the changed data (cant change date)");
        Order newOrder = consoleVeiw.makeOrder();

        try {
            service.replaceOrder(order, newOrder);
        } catch (NoSuchOrder ignored) {
            //can't be reached
        } catch (InvalidInput e) {
            boolean answer = consoleVeiw.userQuestion("Invalid input for new order, tax or product is incorrect, return to menu? y/n");
            if(!answer){this.methodRepeat="edit";}
        }
    }

    private void remove(){
        OrderID orderID = consoleVeiw.findOrder();
        Order order;
        try {
            order = service.getOrder(orderID.date(), orderID.orderNumber());
        } catch (NoSuchOrder e) {
            consoleVeiw.userMessage("no such order");
            return;
        }

        service.deleteOrder(order);
    }
}
