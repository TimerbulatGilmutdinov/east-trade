package ru.itis.easttrade.exceptions;

public class CurrencyApiException extends RuntimeException{
    public CurrencyApiException(String message) {
        super(message);
    }

    public CurrencyApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrencyApiException(Throwable cause) {
        super(cause);
    }
}
