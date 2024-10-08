package com.veryan.FlooringMastery.veiw;

public interface Veiw {
        void menu();
        void displayOrders();
        void makeOrder();
        void displayOrder();
        void userMessage(String message);
        String userQuestion(String question);
        void findOrder(int orderId);
}
