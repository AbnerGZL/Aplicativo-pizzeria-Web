package com.pizzeria.proyecto.Controllers;

import com.pizzeria.proyecto.Models.Repertorio;
import com.pizzeria.proyecto.Service.RepertorioService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class HomeController {

    private final RepertorioService repertorioService;

    public HomeController(RepertorioService repertorioService) {
        this.repertorioService = repertorioService;
    }

    @GetMapping
    public String index(Model model, HttpServletRequest request) {
        String locate = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("locate".equals(cookie.getName()) && cookie.getValue() != null) {
                    locate = (new String(Base64.getUrlDecoder().decode(cookie.getValue()), StandardCharsets.UTF_8)).trim().replaceAll("%20"," ");
                    break;
                }
            }
        }
        Mono<List<Repertorio>> repertoriosMonoPizzas = repertorioService.obtenerRepertorios("Pizzas", 8);
        List<Repertorio> repertoriosPizzas = repertoriosMonoPizzas.block();

        Mono<List<Repertorio>> repertoriosMonoOferta = repertorioService.obtenerRepertorios("Oferta", 8);
        List<Repertorio> repertoriosOfertas = repertoriosMonoOferta.block();
        model.addAttribute("pizzas", repertoriosPizzas);
        model.addAttribute("ofertas", repertoriosOfertas);
        model.addAttribute("locate", locate);
        return "Home";
    }

    @GetMapping("/{categoria}")
    public String mostrarRepertorio(@PathVariable("categoria") String categoria, Model model) {
        Map<String, Map<String, String>> categorias = Map.of(
                "oferts", Map.of("filtro", "ofertas", "titulo", "Promociones solo para ti"),
                "combos", Map.of("filtro", "combos", "titulo", "Los mejores combos"),
                "drinks", Map.of("filtro", "bebidas", "titulo", "Escoge tu bebida favorita"),
                "snacks", Map.of("filtro", "antojitos", "titulo", "Tus aperivitos favoritos"),
                "unbeatables", Map.of("filtro", "imbatibles", "titulo", "Nuestros combos más grandes"),
                "single", Map.of("filtro", "para mi", "titulo", "Personaliza tu pedido"),
                "pizzas", Map.of("filtro", "pizzas", "titulo", "La especialidad de la casa")
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

        return "Repertory";
    }

    @PostMapping("/locate")
    public String locate(@RequestParam("locate") String locate, @RequestParam("change") String change, HttpServletResponse response) {
        if(locate != null) {
            try {
                locate = locate.trim().replaceAll("\\s+", "%20");
                String encodedLocate = Base64.getUrlEncoder().encodeToString(locate.getBytes(StandardCharsets.UTF_8));

                Cookie locateCookie = new Cookie("locate", encodedLocate);
                locateCookie.setHttpOnly(true);
                locateCookie.setMaxAge(7 * 24 * 60 * 60);

                response.addCookie(locateCookie);
                return !Objects.equals(change, "not") ? "redirect:/" : "redirect:/oferts";

            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/";
            }
        }
        return "redirect:/";

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
