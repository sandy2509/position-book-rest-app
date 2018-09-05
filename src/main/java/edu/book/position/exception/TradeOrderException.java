package edu.book.position.exception;

public class TradeOrderException extends Exception{

    private static final long serialVersionUID = 1L;

    public TradeOrderException(String msg) {
        super(msg);
    }

    public TradeOrderException(String msg, Throwable cause) {
        super(msg, cause);
    }
}