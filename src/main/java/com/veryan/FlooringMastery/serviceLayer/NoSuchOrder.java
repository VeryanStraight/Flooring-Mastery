package com.veryan.FlooringMastery.serviceLayer;

public class NoSuchOrder extends Exception{
    public NoSuchOrder() {}

    // Constructor that accepts a message
    public NoSuchOrder(String message)
    {
        super(message);
    }
}
