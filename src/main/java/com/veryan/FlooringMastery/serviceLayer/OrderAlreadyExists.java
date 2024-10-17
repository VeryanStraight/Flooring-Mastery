package com.veryan.FlooringMastery.serviceLayer;

/**
 * an exception for trying to add an already existing order
 */
public class OrderAlreadyExists extends Exception{

    /**
     * a constructor that adds a message to the exception
     * @param message the message to add
     */
    public OrderAlreadyExists(String message)
    {
        super(message);
    }
}
