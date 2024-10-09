package com.veryan.FlooringMastery.veiw;
import com.veryan.FlooringMastery.model.Order;
import com.veryan.FlooringMastery.model.OrderID;
import com.veryan.FlooringMastery.model.Product;
import com.veryan.FlooringMastery.model.Tax;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Component
public class ConsoleVeiw implements Veiw {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String menu() {
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n" +
                "  * <<Flooring Program>>\n" +
                "  * 1. Display Orders\n" +
                "  * 2. Add an Order\n" +
                "  * 3. Edit an Order\n" +
                "  * 4. Remove an Order\n" +
                "  * 5. Export All Data\n" +
                "  * 6. Quit\n" +
                "  *\n" +
                "  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n" +
                "  Input the name of an option:");

        return scanner.next();
    }

    @Override
    public void displayOrders(List<Order> orders) {
        System.out.println("\n\n==============================================================================\n\n");
        for(Order o: orders){
            displayOrder(o);
        }
    }

    @Override
    public Order makeOrder() {
        System.out.println("\n\n==============================================================================\n\n");
        System.out.println("Input date in format dd/mm/yyyy: ");
        LocalDate date = LocalDate.parse(scanner.next(), formatter);
        System.out.println("Input customer name: ");
        String customerName = scanner.next();
        System.out.println("Input state code: ");
        Tax tax = new Tax(scanner.next());
        System.out.println("Input product name: ");
        Product product = new Product(scanner.next());
        System.out.println("Input area: ");
        BigDecimal area = new BigDecimal(scanner.next());

        return new Order(date,customerName,tax,product,area);
    }

    @Override
    public void displayOrder(Order order) {
        System.out.println("\n\n==============================================================================\n\n");
        System.out.println(order);
    }

    @Override
    public void userMessage(String message) {
        System.out.println("\n\n==============================================================================\n\n");
        System.out.println(message);

    }

    @Override
    public Boolean userQuestion(String question) {
        System.out.println("\n\n==============================================================================\n\n");
        userMessage(question);
        System.out.println("Type y or n: ");
        String answer = scanner.next();
        return answer.equals("y");
    }

    @Override
    public OrderID findOrder() {
        System.out.println("\n\n==============================================================================\n\n");
        System.out.println("Order date in dd/mm/yyyy format: ");
        LocalDate date = LocalDate.parse(scanner.next(), formatter);
        System.out.println("order number: ");
        int num = scanner.nextInt();
        return new OrderID(date, num);
    }
}
