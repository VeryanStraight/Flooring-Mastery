package com.veryan.FlooringMastery.dao;

import com.veryan.FlooringMastery.model.Order;
import com.veryan.FlooringMastery.model.Product;
import com.veryan.FlooringMastery.model.Tax;
import com.veryan.FlooringMastery.serviceLayer.ServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ServiceImplTest {
    private Connection conn;
    private final List<Order> testOrders = new ArrayList<>();
    private final List<Tax> testTaxes = new ArrayList<>();
    private final List<Product> testProducts = new ArrayList<>();

    private final JdbcDao dao = new JdbcDao("testflooringmastery");

    private final ServiceImpl service = new ServiceImpl(dao);
    @BeforeEach
    public void setUp(){
        //TODO: create a database test
        String url = "jdbc:postgresql://localhost:5432/testflooringmastery";
        String username = "postgres";
        String password = "password";

        try {
            this.conn = DriverManager.getConnection(url, username, password);

            File myObj = new File("src/test/resources/loadDatabase.txt");
            Scanner scanner = new Scanner(myObj);
            String sql = scanner.tokens().collect(Collectors.joining(" "));

            PreparedStatement query = conn.prepareStatement(sql);
            query.executeUpdate();

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

    @AfterEach
    public void cleanUp(){
        try {
            PreparedStatement stmt = conn.prepareStatement("DROP TABLE Orders; " +
                                                                "DROP TABLE Taxes; " +
                                                                "DROP TABLE Products; ");
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeOrderTest(){
        service.deleteOrder(testOrders.get(3));

        //TODO: should use query to get orders (otherwise test could fail due to getOrders() issue not removeOrder() issue)
        List<Order> orders = dao.getOrders();
        assertFalse(orders.contains(testOrders.get(3)));
    }
}
