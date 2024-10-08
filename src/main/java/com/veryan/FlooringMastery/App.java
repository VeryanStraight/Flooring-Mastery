package com.veryan.FlooringMastery;

import com.veryan.FlooringMastery.controller.FlooringController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        FlooringController flooringController = context.getBean(FlooringController.class);
        flooringController.run();
    }
}

