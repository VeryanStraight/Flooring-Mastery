package com.veryan.FlooringMastery.dao;

import com.veryan.FlooringMastery.model.Order;
import com.veryan.FlooringMastery.model.Product;
import com.veryan.FlooringMastery.model.Tax;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JdbcDaoTest {

    private Connection conn;
    private final List<Order> testOrders = new ArrayList<>();
    private final List<Tax> testTaxes = new ArrayList<>();
    private final List<Product> testProducts = new ArrayList<>();

    private final JdbcDao dao = new JdbcDao("testflooringmastery");
    @BeforeEach
    public void setUp(){
        //TODO: create a database test
        String url = "jdbc:mysql://localhost:3306/testflooringmastery?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "password";

        try {
            this.conn = DriverManager.getConnection(url, username, password);

            executeUpdateFromFile("src/test/resources/loadDataSQL/loadTaxes.txt");
            executeUpdateFromFile("src/test/resources/loadDataSQL/loadProducts.txt");
            executeUpdateFromFile("src/test/resources/loadDataSQL/loadOrders.txt");
            executeUpdateFromFile("src/test/resources/loadDataSQL/addTaxes.txt");
            executeUpdateFromFile("src/test/resources/loadDataSQL/addProducts.txt");
            executeUpdateFromFile("src/test/resources/loadDataSQL/addOrders.txt");


        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }

        testProducts.add(new Product("laminate", new BigDecimal("1.75"), new BigDecimal("2.10")));
        testProducts.add(new Product("wood", new BigDecimal("5.15"), new BigDecimal("4.75")));
        testProducts.add(new Product("carpet", new BigDecimal("2.25"), new BigDecimal("2.10")));
        testProducts.add(new Product("tile", new BigDecimal("3.50"), new BigDecimal("4.15")));

        testTaxes.add(new Tax("WA", "Washington", new BigDecimal("9.25")));
        testTaxes.add(new Tax("TX", "Texas", new BigDecimal("4.45")));
        testTaxes.add(new Tax("KY", "Kentucky", new BigDecimal("6.00")));
        testTaxes.add(new Tax("CA", "California", new BigDecimal("25.00")));

        testOrders.add(new Order(LocalDate.parse("3027-08-01"), 1, "cust1"
                                ,testTaxes.get(1), testProducts.get(0), new BigDecimal("205")));
        testOrders.add(new Order(LocalDate.parse("3027-08-01"), 2, "cust2"
                ,testTaxes.get(0), testProducts.get(0), new BigDecimal("105")));
        testOrders.add(new Order(LocalDate.parse("3013-08-01"), 1, "cust3"
                ,testTaxes.get(0), testProducts.get(1), new BigDecimal("243")));
        testOrders.add(new Order(LocalDate.parse("2013-06-02"), 1, "Albert Einstein"
                ,testTaxes.get(2), testProducts.get(2), new BigDecimal("217.0")));
        testOrders.add(new Order(LocalDate.parse("2013-06-01"), 0, "Ada Lovelace"
                ,testTaxes.get(3), testProducts.get(3), new BigDecimal("249.00")));

    }

    /**
     * load a query from a file and execute it
     * @param fileName path to the file
     */
    private void executeUpdateFromFile(String fileName) throws FileNotFoundException, SQLException {
        File myObj = new File(fileName);
        Scanner scanner = new Scanner(myObj);
        String sql = scanner.tokens().collect(Collectors.joining(" "));
        System.out.println(sql);
        PreparedStatement query = conn.prepareStatement(sql);
        query.executeUpdate();
    }

    @AfterEach
    public void cleanUp(){
        try {
            PreparedStatement stmt = conn.prepareStatement("DROP TABLE Orders");
            stmt.executeUpdate();
            stmt = conn.prepareStatement("DROP TABLE Taxes");
            stmt.executeUpdate();
            stmt = conn.prepareStatement("DROP TABLE Products");
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getOrdersTest(){
        List<Order> orders = dao.getOrders();
        assertEquals(orders.size(), 3);
        List<Order> li = List.of(testOrders.get(2), testOrders.get(3), testOrders.get(4));
        assertTrue(orders.containsAll(li));
    }


    @Test
    public void addOrderTest(){
        Order order = testOrders.get(0);

        dao.addOrder(order);

        //TODO: should use query to get orders (otherwise test could fail due to getOrders() issue not addOrder issue)
        List<Order> orders = dao.getOrders();
        assertTrue(orders.contains(order));
    }

}
