package com.veryan.FlooringMastery.serviceLayer;

public class InvalidInput extends Exception {
    public InvalidInput() {}

    // Constructor that accepts a message
    public InvalidInput(String message)
    {
        super(message);
    }

}
