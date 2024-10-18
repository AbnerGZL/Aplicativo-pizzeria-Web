package com.example.proyecto.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String index(){
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
}
