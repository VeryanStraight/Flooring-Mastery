package com.veryan.FlooringMastery;

import com.veryan.FlooringMastery.controller.FlooringController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * an app for managing orders for a flooring company
 */
public class App {
    /**
     * the method that runs the program
     * @param args the main arguments
     */
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        FlooringController flooringController = context.getBean(FlooringController.class);
        flooringController.run();
    }
}

