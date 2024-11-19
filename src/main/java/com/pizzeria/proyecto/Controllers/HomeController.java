package com.pizzeria.proyecto.Controllers;

import com.pizzeria.proyecto.Models.Cliente;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor

public class HomeController {

    @GetMapping
    public String index(Model model){
        return "Home";
    }

    @GetMapping("oferts")
    public String oferts(){
        return "Oferts";
    }

    @GetMapping("pizzas")
    public String pizzas(){
        return "Pizzas";
    }

    @GetMapping("drinks")
    public String drinks(){
        return "Drinks";
    }

    @GetMapping("single")
    public String single(){
        return "ForMe";
    }

    @GetMapping("combos")
    public String combos(){
        return "Combos";
    }

    @GetMapping("snacks")
    public String snacks(){
        return "Snacks";
    }

    @GetMapping("unbeatables")
    public String unbeatables(){
        return "Unbeatables";
    }

    @GetMapping("car")
    public String car(){
        return "Car";
    }

    @GetMapping("login")
    public String login(){
        return "Login";
    }

    @GetMapping("register")
    public String register(){
        return "Register";
    }

    @PostMapping("/registro")
    public String registrarCliente(@ModelAttribute Cliente cliente, Model model) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        // Crea el cuerpo de la solicitud como un objeto JSON
//        String jsonBody = "{"
//                + "\"USUARIO\": \"" + cliente.getUsuario() + "\", "
//                + "\"CORREO\": \"" + cliente.getCorreo() + "\", "
//                + "\"TELEFONO\": " + cliente.getTelefono() + ", "
//                + "\"CONTRASEÑA\": \"" + cliente.getContrasena() + "\""
//                + "}";
//
//        // Configura el body y los headers
//        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
//
//        // Realiza la solicitud POST al API de Django
//        try {
//            ResponseEntity<String> response = restTemplate.exchange(register_url, HttpMethod.POST, entity, String.class);
//
//            // Procesa la respuesta
//            if (response.getStatusCode() == HttpStatus.CREATED) {
//                model.addAttribute("mensaje", "Usuario registrado exitosamente");
//                return "registroExitoso";
//            } else {
//                model.addAttribute("mensaje", "Error al registrar el usuario");
//                return "errorRegistro";
//            }
//        } catch (Exception e) {
//            model.addAttribute("mensaje", "Error al conectar con el servidor");
//            return "errorRegistro";
//        }
        return null;
    }
}
