package com.veryan.FlooringMastery.dao;

import com.veryan.FlooringMastery.model.Order;
import com.veryan.FlooringMastery.model.Product;
import com.veryan.FlooringMastery.model.Tax;
import com.veryan.FlooringMastery.serviceLayer.NoSuchOrder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * a JDBC implementation of the dao
 */
@Component
public class JdbcDao implements Dao{

    private final Connection con;

    /**
     * connects to the database
     * @param database the name of the database to connect to
     * @return the connection
     */
    private Connection makeConnection(String database){
        String url = "jdbc:mysql://localhost:3306/"+database+"?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "password";
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException ignored) {
            //TODO: add logging
        }
        return connection;
    }

    @Override
    public boolean loadedData(){
        return con != null;
    }


    /**
     * constructor (for Spring to use)
     * automatically uses flooringmastery database
     */
    JdbcDao(){
        this.con = makeConnection("flooringmastery");
    }

    /**
     * constructor that allows you to specify what database to use
     * @param database the name of the database
     */
    JdbcDao(String database){
        this.con = makeConnection(database);
    }

    @Override
    public void saveAndClose() throws DaoException {
        try {
            con.close();
        } catch (SQLException e) {
            //TODO: do something
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> getOrders() {
        String query = "SELECT * FROM Orders o\n" +
                "JOIN Products p ON p.producttype = o.producttype\n" +
                "JOIN Taxes t ON t.state = o.state";
        List<Order> orders = new ArrayList<>();


        try (PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                LocalDate date = LocalDate.parse(rs.getString("date"));
                orders.add(creatOrder(rs,date));
            }

        } catch (SQLException e) {
            //TODO: log it instead
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * creates an order (and it's tax and product) from a result set and a given date
     * @param rs the result set
     * @param date the date
     * @return the new order
     * @throws SQLException if the result set is missing a column
     */
    private Order creatOrder(ResultSet rs, LocalDate date) throws SQLException {
        int orderNumber = rs.getInt("OrderNumber");
        String customerName = rs.getString("CustomerName");
        String state = rs.getString("State");
        String stateName = rs.getString("StateName");
        BigDecimal taxRate = rs.getBigDecimal("TaxRate");
        String productType = rs.getString("ProductType");
        BigDecimal costPerSquareFoot = rs.getBigDecimal("CostPerSquareFoot");
        BigDecimal laborCostPerSquareFoot = rs.getBigDecimal("LaborCostPerSquareFoot");
        BigDecimal area = rs.getBigDecimal("Area");

        Tax t = new Tax(state,stateName,taxRate);
        Product p = new Product(productType,costPerSquareFoot,laborCostPerSquareFoot);

        return new Order(date,orderNumber,customerName,t,p,area);
    }

    @Override
    public List<Order> getOrders(LocalDate date) {
        String query = "SELECT * FROM Orders o\n" +
                        "JOIN Products p ON p.producttype = o.producttype\n" +
                        "JOIN Taxes t ON t.state = o.state " +
                        "WHERE o.date = ?";
        List<Order> orders = new ArrayList<>();

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setDate(1, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                orders.add(creatOrder(rs, date));
            }

        } catch (SQLException e) {
            //TODO: log it instead
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public Map<String, Tax> getTaxes() {
        String query = "SELECT * FROM Taxes";
        Map<String, Tax> taxes = new HashMap<>();


        try (PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String state = rs.getString("State");
                String stateName = rs.getString("StateName");
                BigDecimal taxRate = rs.getBigDecimal("TaxRate");

                Tax t = new Tax(state,stateName,taxRate);

                taxes.put(state, t);
            }

        } catch (SQLException e) {
            //TODO: log it instead
            e.printStackTrace();
        }
        return taxes;

    }

    @Override
    public Map<String, Product> getProducts() {
        String query = "SELECT * FROM Products";
        Map<String, Product> products = new HashMap<>();


        try (PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String productType = rs.getString("ProductType");
                BigDecimal costPerSquareFoot = rs.getBigDecimal("CostPerSquareFoot");
                BigDecimal laborCostPerSquareFoot = rs.getBigDecimal("LaborCostPerSquareFoot");

                Product p = new Product(productType,costPerSquareFoot,laborCostPerSquareFoot);

                products.put(productType, p);
            }

        } catch (SQLException e) {
            //TODO: log it instead
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) throws NoSuchOrder {
        String query = "SELECT * FROM Orders o " +
                        "JOIN Taxes t ON t.state = o.state " +
                        "JOIN Products p ON p.productType = o.productType " +
                        "WHERE o.date = ? AND o.OrderNumber = ?";
        Order o = null;

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setDate(1, Date.valueOf(date));
            stmt.setInt(2, orderNumber);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                o = creatOrder(rs, date);

            }

        } catch (SQLException e) {
            //TODO: log it instead
            e.printStackTrace();
        }

        if(o==null){
            throw new NoSuchOrder();
        }
        return o;

    }

    @Override
    public void addOrder(Order order) {
        String getOrderNumQuery = "SELECT MAX(orderNumber) OrderNumber " +
                "FROM orders " +
                "WHERE date = ?";


        String query = "INSERT INTO Orders (" +
                "    date, OrderNumber, CustomerName, State, ProductType, Area, " +
                "    MaterialCost, LaborCost, Tax, Total" +
                ") VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = con.prepareStatement(getOrderNumQuery);
            stmt.setDate(1, Date.valueOf(order.date));
            ResultSet result = stmt.executeQuery();
            int orderNum = 0;
            while (result.next()){
                orderNum = result.getInt("OrderNumber");
            }

            stmt = con.prepareStatement(query);
            stmt.setDate(1, Date.valueOf(order.date));
            stmt.setInt(2, orderNum);
            stmt.setString(3, order.customerName);
            stmt.setString(4, order.tax.state);
            stmt.setString(5,order.product.productType);
            stmt.setBigDecimal(6, order.area);
            stmt.setBigDecimal(7, order.materialCost);
            stmt.setBigDecimal(8, order.laborCost);
            stmt.setBigDecimal(9, order.taxTotal);
            stmt.setBigDecimal(10, order.total);


            int rs = stmt.executeUpdate();

        } catch (SQLException e) {
            //TODO: log it instead
            e.printStackTrace();
        }

    }

    @Override
    public void replaceOrder(Order oldOrder, Order newOrder) throws NoSuchOrder {
        String query = "UPDATE Orders SET " +
                            "CustomerName = ?,State = ?, ProductType = ?, Area = ?," +
                            "MaterialCost = ?, LaborCost = ?, Tax = ?, Total = ? " +
                        "WHERE date = ? AND orderNumber = ? ";

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, newOrder.customerName);
            stmt.setString(2, newOrder.tax.state);
            stmt.setString(3,newOrder.product.productType);
            stmt.setBigDecimal(4, newOrder.area);
            stmt.setBigDecimal(5, newOrder.materialCost);
            stmt.setBigDecimal(6, newOrder.laborCost);
            stmt.setBigDecimal(7, newOrder.taxTotal);
            stmt.setBigDecimal(8, newOrder.total);
            stmt.setDate(9, Date.valueOf(oldOrder.date));
            stmt.setInt(10, oldOrder.orderNumber);


            int rs = stmt.executeUpdate();

            assert rs <= 1: "replaced more than one order";
            if(rs == 0){throw new NoSuchOrder();}

        } catch (SQLException e) {
            //TODO: log it instead
            e.printStackTrace();
        }

    }

    @Override
    public void removeOrder(Order order) {
        String query = "DELETE FROM Orders WHERE date = ? AND OrderNumber = ?; ";

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setDate(1,Date.valueOf(order.date));
            stmt.setInt(2, order.orderNumber);


            int rs = stmt.executeUpdate();
            assert rs <= 1: "deleted more than one order";


        } catch (SQLException e) {
            //TODO: log it instead
            e.printStackTrace();
        }

    }

    @Override
    public boolean orderExists(Order order) {
        try {
            getOrder(order.date, order.orderNumber);
            return true;
        } catch (NoSuchOrder e) {
            return false;
        }
    }
}
