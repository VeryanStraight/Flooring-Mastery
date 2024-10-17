package com.veryan.FlooringMastery.dao;

/**
 * an exception for anything that goes wrong in the dao layer related to accessing data
 * used to hide the dao implementation
 */
public class DaoException extends Exception{
    DaoException(){}
    DaoException(String message){super(message);}
}
