package ru.itis.easttrade.exceptions;

public class AlreadyExistsException extends RuntimeException {
    private Object model;
    public AlreadyExistsException(String message) {
        super(message);
    }
    public AlreadyExistsException(String message, Object model){
        super(message);
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
