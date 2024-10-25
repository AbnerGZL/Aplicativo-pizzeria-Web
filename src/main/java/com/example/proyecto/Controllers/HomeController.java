package com.example.proyecto.Controllers;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/user"; // Redirige al usuario autenticado
        }
        return "Login";
    }

    @GetMapping("register")
    public String register(){
        return "Register";
    }
}
