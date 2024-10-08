package com.veryan.FlooringMastery.serviceLayer;

public class OrderAlreadyExists extends Exception{
    public OrderAlreadyExists() {}

    // Constructor that accepts a message
    public OrderAlreadyExists(String message)
    {
        super(message);
    }
}
