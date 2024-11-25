package com.pizzeria.proyecto.Controllers;

import com.pizzeria.proyecto.Models.Repertorio;
import com.pizzeria.proyecto.Service.RepertorioService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.*;
import reactor.core.publisher.Mono;
import java.util.Map;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    private final RepertorioService repertorioService;

    public HomeController(RepertorioService repertorioService) {
        this.repertorioService = repertorioService;
    }

    @GetMapping
    public String index(Model model){
        Mono<List<Repertorio>> repertoriosMonoPizzas = repertorioService.obtenerRepertorios("Pizzas", 8);
        List<Repertorio> repertoriosPizzas = repertoriosMonoPizzas.block();

        Mono<List<Repertorio>> repertoriosMonoOferta = repertorioService.obtenerRepertorios("Oferta", 8);
        List<Repertorio> repertoriosOfertas = repertoriosMonoOferta.block();
        model.addAttribute("pizzas", repertoriosPizzas);
        model.addAttribute("ofertas", repertoriosOfertas);
        return "Home";
    }

    @GetMapping("/{categoria}")
    public String mostrarRepertorio(@PathVariable("categoria") String categoria, Model model) {
        Map<String, Map<String, String>> categorias = Map.of(
                "oferts", Map.of("filtro", "Ofertas", "titulo", "Promociones solo para ti"),
                "combos", Map.of("filtro", "Combos", "titulo", "Los mejores combos"),
                "drinks", Map.of("filtro", "Bebidas", "titulo", "Escoge tu bebida favorita"),
                "snacks", Map.of("filtro", "Antojitos", "titulo", "Tus aperivitos favoritos"),
                "unbeatables", Map.of("filtro", "Imbatibles", "titulo", "Nuestros combos más grandes"),
                "single", Map.of("filtro", "Para mi", "titulo", "Personaliza tu pedido"),
                "pizzas", Map.of("filtro", "Pizzas", "titulo", "La especialidad de la casa")
        );

        if (!categorias.containsKey(categoria)) {
            return "redirect:/error";
        }

        String filtro = categorias.get(categoria).get("filtro");
        String titulo = categorias.get(categoria).get("titulo");

        Mono<List<Repertorio>> repertoriosMono = repertorioService.obtenerRepertorios(filtro, 6);
        List<Repertorio> repertorios = repertoriosMono.block();

        model.addAttribute("titulo", titulo);
        model.addAttribute("repertorios", repertorios);

        return "lista_repertorio";
    }

    @GetMapping("car")
    public String car(){
        return "Car";
    }

    @GetMapping("selector_product")
    public String selectorProductos(){
        return "Selector-productos";
    }

    @GetMapping("selector")
    public String selector(){
        return "Selector";
    }
}
