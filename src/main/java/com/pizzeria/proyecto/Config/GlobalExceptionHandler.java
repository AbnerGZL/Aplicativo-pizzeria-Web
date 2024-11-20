package com.pizzeria.proyecto.Config;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public String handleException(Exception e, Model model) {
//        e.printStackTrace();
//        model.addAttribute("error", "Ocurrió un error inesperado. Por favor, inténtalo nuevamente.");
//        return "login";
//    }
//
//    @ExceptionHandler(org.springframework.web.client.HttpClientErrorException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public String handleBadRequestException(org.springframework.web.client.HttpClientErrorException e, Model model) {
//        model.addAttribute("error", "Credenciales incorrectas o solicitud inválida.");
//        return "login";
//    }
//
//    @ExceptionHandler(org.springframework.web.client.HttpServerErrorException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public String handleInternalServerErrorException(org.springframework.web.client.HttpServerErrorException e, Model model) {
//        model.addAttribute("error", "Error en el servidor. Intenta nuevamente.");
//        return "login";
//    }
//
//    @ExceptionHandler(org.springframework.web.client.HttpStatusCodeException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public String handleNotFoundException(org.springframework.web.client.HttpStatusCodeException e, Model model) {
//        model.addAttribute("error", "Recurso no encontrado.");
//        return "login";
//    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String UnauthorizedException(Exception e, Model model) {
        e.printStackTrace();
        model.addAttribute("error", "Credenciales incorrectas");
        return "login";
    }
}

