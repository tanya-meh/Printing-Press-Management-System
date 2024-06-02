package org.informatics_java.exceptions;

public class IllegalAmountOfMoneyException extends RuntimeException{
    public IllegalAmountOfMoneyException(String errorMessage) {
        super(errorMessage);
    }
}
