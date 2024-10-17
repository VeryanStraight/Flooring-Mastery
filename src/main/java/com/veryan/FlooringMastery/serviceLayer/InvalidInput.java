package com.veryan.FlooringMastery.serviceLayer;

/**
 * an exception for when invalid input is given to the service layer
 */
public class InvalidInput extends Exception {

    /**
     * a constructor that adds a message to the exception
     * @param message the message to add
     */
    public InvalidInput(String message)
    {
        super(message);
    }

}
