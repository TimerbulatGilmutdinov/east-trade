package ru.itis.easttrade.aspects;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.itis.easttrade.exceptions.AlreadyExistsException;

@ControllerAdvice
public class AlreadyExistsExceptionHandler {
    @ExceptionHandler(AlreadyExistsException.class)
    public String handleAlreadyExistsException(AlreadyExistsException ex, Model model){
        model.addAttribute("account", ex.getModel());
        model.addAttribute("errorMessage",ex.getMessage());
        return "registration";
    }
}
