package com.veryan.FlooringMastery.model;

import java.time.LocalDate;

/**
 * a record for identifying a unique order
 * @param date the order date
 * @param orderNumber the order number
 */
public record OrderID (LocalDate date, int orderNumber){}