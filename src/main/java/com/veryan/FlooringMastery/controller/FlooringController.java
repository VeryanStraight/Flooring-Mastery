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

    /**
     * initialises the controller (used by spring when starting app)
     * @param service the service object that allows the needed CRUD methods
     * @param view the view object to communicate with user
     */
    @Autowired
    public FlooringController(Service service, Veiw view) {
        this.service = service;
        this.veiw = view;
    }

    /**
     * method that runs the app
     */
    public void run() {
        if(!service.loadedData()){
            veiw.userMessage("Error loading data");
            running = false;
        }
        while (running) {

            //check if we should repeat a option or go back to menu
            String action;
            if (methodRepeat.isEmpty()) {
                action = veiw.menu().toLowerCase();
            } else {
                action = methodRepeat;
                methodRepeat = "";
            }

            //do chosen option
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

    /**
     * the display option
     * It asks the user for a date and then display the orders for that date.
     * If no orders exist for that date, it will display an error message and return the user to the main menu.
     */
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

    /**
     * the add option
     * It will query the user for each piece of order data necessary, then add the order if valid, if not it will ask
     * to return to menu or repeat.
     * <p>
     * Order Date – Must be in the future
     * Customer Name – May not be blank
     * State – must belong to an existing tax object.
     * Product Type – must belong to an existing product object.
     * Area – The area must be a positive number. Minimum order size is 100.
     * Shows a summary of the order once the calculations are completed and prompt the user as to whether they want to
     * place the order (Y/N). If yes, the data will be added to in-memory storage. If no, simply return to the main menu.
     *
     */
    private void add(){
        Order order = veiw.makeOrder(service.getProducts());

        try {
            //validate the order
            order = service.initliseOrder(order);

            //ask user conformation then add order
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

    /**
     * The Edit option
     * Edit will ask the user for a date and order number.
     * If the order exists for that date, it will display the found order then it will ask the user for each piece of
     * order data that can change, CustomerName, State, ProductType, Area.
     * After getting the data it will show the new order and confirm the edit, then save or return to menu.
     */
    private void edit(){
        //get user input to find the old order
        OrderID orderID = veiw.findOrder();
        if(orderID.date() == null || orderID.orderNumber() < 0){
            veiw.userMessage("Invalid input");
            this.methodRepeat="edit";
            return;
        }

        //use the users input to get the old order
        Order order;
        try {
            order = service.getOrder(orderID.date(), orderID.orderNumber());
        } catch (InvalidInput|NoSuchOrder e) {
            boolean answer = veiw.userQuestion("Cant find Order, return to menu? y/n");
            if(!answer){this.methodRepeat="edit";}
            return;
        }

        //display the old order and ask for the modified information
        veiw.userMessage("Old Order");
        veiw.displayOrder(order);
        veiw.userMessage("input the changed data (cant change date)");
        Order newOrder = veiw.makeOrder(service.getProducts(), order.date);
        newOrder.orderNumber = order.orderNumber;

        try {
            //validate new order, display it and confirm edit
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

    /**
     * remove order option
     * it ask for the date and order number.
     * If it exists, it prints the order and confirms the delete.
     * If yes, it should be removed from the list.
     */
    private void remove(){
        OrderID orderID = veiw.findOrder();
        Order order;
        try {
            order = service.getOrder(orderID.date(), orderID.orderNumber());
            veiw.displayOrder(order);
        } catch (InvalidInput|NoSuchOrder e) {
            veiw.userMessage("no such order");
            return;
        }

        boolean answer = veiw.userQuestion("delete order? y/n");

        if(answer) {
            service.deleteOrder(order);
        }
    }
}
