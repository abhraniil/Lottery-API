package com.project.lottery.exception;

public class CannotAmendCheckedTicketException extends RuntimeException {
    public CannotAmendCheckedTicketException(String message) {
        super(message);
    }
}
