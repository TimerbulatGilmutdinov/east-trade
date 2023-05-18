package ru.itis.easttrade.exceptions;

public class ModelAttributeValidationException extends RuntimeException {
    public ModelAttributeValidationException(String message) {
        super(message);
    }

    public ModelAttributeValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelAttributeValidationException(Throwable cause) {
        super(cause);
    }
}
