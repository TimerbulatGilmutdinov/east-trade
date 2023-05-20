package ru.itis.easttrade.aspects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException;
import ru.itis.easttrade.dto.ExceptionDto;
import ru.itis.easttrade.exceptions.AlreadyExistsException;
import ru.itis.easttrade.exceptions.NotFoundException;


@ControllerAdvice
public class ExceptionsHandler {
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException ex, Model model){
        model.addAttribute("errorMessage", ex.getMessage());
        return "denied";
    }
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NotFoundException ex, Model model){
        model.addAttribute("errorMessage",ex.getMessage());
        return "not-found";
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleAlreadyExistsException(AlreadyExistsException ex, Model model){
        model.addAttribute("account", ex.getModel());
        model.addAttribute("errorMessage",ex.getMessage());
        return "registration";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public String handleInternalServerError(){
        return "error";
    }

//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<ExceptionDto> handleNotFound(NotFoundException ex) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(ExceptionDto.builder()
//                        .message(ex.getMessage())
//                        .status(HttpStatus.NOT_FOUND.value())
//                        .build());
//    }
}
