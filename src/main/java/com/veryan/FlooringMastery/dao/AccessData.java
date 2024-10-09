package com.veryan.FlooringMastery.dao;

import com.veryan.FlooringMastery.model.Order;
import com.veryan.FlooringMastery.model.Product;
import com.veryan.FlooringMastery.model.Tax;
import com.veryan.FlooringMastery.serviceLayer.NoSuchOrder;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AccessData implements dao{
    private final HashMap<LocalDate, ArrayList<Order>> orders = new HashMap<>();
    private final HashMap<String, Tax> taxes = new HashMap<>();
    private final HashMap<String, Product> products = new HashMap<>();
    private String filePath = "src/main/resources";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");


    AccessData() throws FileException{
        loadData();
    }

    AccessData(String filePath) throws FileException {
        this.filePath = filePath;
        System.out.println("Current working directory: " + System.getProperty("user.dir"));
        loadData();
    }


    private void loadData() throws FileException{
        loadProducts();
        loadTax();
        loadOrders();

    }

    private void loadOrders() throws FileException{
        String directory = filePath+"/data/Orders";
        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directory), "Orders_*.txt");

            for (Path filePath : stream) {
                // Extract date from file name
                String fileName = filePath.getFileName().toString();
                String dateString = fileName.substring("Orders_".length(), fileName.length() - 4);
                LocalDate date = LocalDate.parse(dateString, formatter);

                ArrayList<Order> dateOrders = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
                    String line = reader.readLine(); // Skip header
                    while ((line = reader.readLine()) != null) {
                        String[] fields = line.split(",");
                        // Assume fields[1] contains CSV representation of Order, reconstruct order
                        String[] values = line.split(",");
                        Order order = orderFromCSV(values, date);
                        dateOrders.add(order);
                    }
                }

                orders.put(date, dateOrders);
            }


        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }

    }

    private Order orderFromCSV(String[] csvStrings, LocalDate date){
        String custName = csvStrings[1];
        Tax tax = taxes.get(csvStrings[2]);
        Product product = products.get(csvStrings[4]);

        double area = Double.parseDouble(csvStrings[5]);
        BigDecimal costPerSquareFoot = new BigDecimal(csvStrings[6]);
        BigDecimal laborCostPerSquareFoot = new BigDecimal(csvStrings[7]);
        BigDecimal materialCost = new BigDecimal(csvStrings[8]);
        BigDecimal laborCostTax = new BigDecimal(csvStrings[9]);
        BigDecimal totalTax = new BigDecimal(csvStrings[10]);
        BigDecimal total = new BigDecimal(csvStrings[11]);

        return new Order(date, custName, tax,product,area,costPerSquareFoot,
                laborCostPerSquareFoot,materialCost,laborCostTax,totalTax,total);

    }

    private void loadTax() throws FileException{
        String filePath = this.filePath+"/data/Data/Taxes.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                // Parse each line as a Product object
                String[] values = line.split(",");
                Tax t = new Tax(values[0], values[1], new BigDecimal(values[2]));
                taxes.put(values[0], t);
            }
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }

    }

    private void loadProducts() throws FileException{
        String filePath = this.filePath+"/data/Data/Products.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                // Parse each line as a Product object
                String[] values = line.split(",");
                Product p = new Product(values[0], new BigDecimal(values[1]), new BigDecimal(values[2]));
                products.put(values[0], p);
            }
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
    }
    @Override
    public void saveData() throws FileException{
        saveOrders();
        saveTaxes();
        saveProducts();
    }

    private void saveProducts() throws FileException{
        String fileName = this.filePath+"/data/Data/Products.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("ProductType,CostPerSquareFoot,LaborCostPerSquareFoot");
            for (Product p : products.values()) {
                // Write each tax to a new line
                writer.newLine();
                writer.write(p.toCSV());
            }
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
    }

    private void saveTaxes() throws FileException{
        String fileName = this.filePath+"/data/Data/Taxes.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("State,StateName,TaxRate");
            for (Tax t : taxes.values()) {
                // Write each tax to a new line
                writer.newLine();
                writer.write(t.toCSV());
            }
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
    }

    private void saveOrders() throws FileException{
        for (Map.Entry<LocalDate, ArrayList<Order>> entry : orders.entrySet()) {
            LocalDate date = entry.getKey();
            List<Order> dateOrders = entry.getValue();

            // Create the file with the date as the name
            String fileName = this.filePath+"/data/Orders/Orders_"+date.format(formatter) + ".txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
                for (int i=0;i<dateOrders.size();i++) {
                    Order o = dateOrders.get(i);
                    // Write each order to a new line
                    writer.newLine();
                    writer.write(i+","+o.toCSV());
                }
            } catch (IOException e) {
                throw new FileException(e.getMessage());
            }
        }
    }

    @Override
    public List<Order> getOrders() {
        ArrayList<Order> tmp = new ArrayList<>();
        for(ArrayList<Order> o: orders.values()){
            tmp.addAll(o);
        }

        return Collections.unmodifiableList(tmp);
    }

    @Override
    public Map<String, Tax> getTaxes() {
        return Collections.unmodifiableMap(taxes);
    }

    @Override
    public Map<String, Product> getProducts() {
        return Collections.unmodifiableMap(products);
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) throws NoSuchOrder {

        if(!orders.containsKey(date)){throw new NoSuchOrder(date+" doesn't exist in orders");}

        ArrayList<Order> dateOrders = orders.get(date);

        if(orderNumber<0 || orderNumber>=dateOrders.size()){throw new NoSuchOrder(orderNumber+" doesn't exist for date "+date);}

        return dateOrders.get(orderNumber);
    }

    @Override
    public void addOrder(Order order) {
        LocalDate date = order.date;
        if(orders.containsKey(date)){
            orders.get(date).add(order);
        }else{
            ArrayList<Order> dateOrders = new ArrayList<>();
            dateOrders.add(order);
            orders.put(date, dateOrders);
        }
    }

    @Override
    public void replaceOrder(Order oldOrder, Order newOrder) throws NoSuchOrder {
        if(!orders.containsKey(oldOrder.date)){throw new NoSuchOrder(oldOrder+" doesn't exist");}

        ArrayList<Order> dateOrders = orders.get(oldOrder.date);
        int i = dateOrders.indexOf(oldOrder);

        if(i==-1){throw new NoSuchOrder(oldOrder+" doesn't exist");}

        dateOrders.set(i, newOrder);
    }

    @Override
    public void removeOrder(Order order){
        ArrayList<Order> dateOrders = orders.get(order.date);
        dateOrders.remove(order);
    }
    @Override
    public boolean orderExists(Order order){
        ArrayList<Order> dateOrders = orders.get(order.date);
        return dateOrders.contains(order);
    }
}
