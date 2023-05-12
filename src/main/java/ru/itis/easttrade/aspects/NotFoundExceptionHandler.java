package ru.itis.easttrade.aspects;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.itis.easttrade.exceptions.AlreadyExistsException;
import ru.itis.easttrade.exceptions.NotFoundException;

@ControllerAdvice
public class NotFoundExceptionHandler extends RuntimeException {
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException ex, Model model){
        model.addAttribute("errorMessage",ex.getMessage());
        return "not-found";
    }
}
