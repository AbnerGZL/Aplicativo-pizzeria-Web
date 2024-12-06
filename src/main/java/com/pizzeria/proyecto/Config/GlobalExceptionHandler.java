package com.pizzeria.proyecto.Config;

import com.pizzeria.proyecto.Exceptions.ApiConnectionException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiConnectionException.class)
    public String handleApiConnectionException(ApiConnectionException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "problems";
    }
}

