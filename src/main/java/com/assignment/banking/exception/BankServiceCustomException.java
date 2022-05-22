package com.assignment.banking.exception;

/**
 * Custom Exception class
 */
public class BankServiceCustomException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BankServiceCustomException(String message) {
        super(message);
    }
}
