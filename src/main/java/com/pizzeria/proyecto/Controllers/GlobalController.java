package com.pizzeria.proyecto.Controllers;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalController {

    @ModelAttribute("sessionAttributes")
    public void addSessionAttributes(HttpSession session, Model model) {
        // Recupera el Map de la sesión o crea uno si no existe
        Map<String, Object> sessionAttributes = (Map<String, Object>) session.getAttribute("sessionAttributes");

        if (sessionAttributes == null) {
            sessionAttributes = new HashMap<>();
            session.setAttribute("sessionAttributes", sessionAttributes);
        }

        // Añadir las variables que deseas en el Map
        sessionAttributes.put("username", session.getAttribute("username"));
        sessionAttributes.put("contrasena", session.getAttribute("contrasena"));
        sessionAttributes.put("correo", session.getAttribute("correo"));
        sessionAttributes.put("telefono", session.getAttribute("telefono"));
        sessionAttributes.put("id", session.getAttribute("id"));

        model.addAttribute("sessionAttributes", sessionAttributes);
    }
}
