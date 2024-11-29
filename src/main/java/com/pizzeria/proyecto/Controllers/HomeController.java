package com.pizzeria.proyecto.Controllers;

import com.pizzeria.proyecto.Models.Repertorio;
import com.pizzeria.proyecto.Models.RepertorioDetalle;
import com.pizzeria.proyecto.Service.RepertorioDetalleService;
import com.pizzeria.proyecto.Service.RepertorioService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final RepertorioService repertorioService;
    private final RepertorioDetalleService repertorioDetalleService;

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
        Mono<List<Repertorio>> repertoriosMonoPizzas = repertorioService.obtenerRepertorios("pizzas");
        List<Repertorio> repertoriosPizzas = repertoriosMonoPizzas.block();

        Mono<List<Repertorio>> repertoriosMonoOferta = repertorioService.obtenerRepertorios("ofertas");
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

        Mono<List<Repertorio>> repertoriosMono = repertorioService.obtenerRepertorios(filtro);
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

    @GetMapping("selector_products")
    public String selectorProductos(
            @RequestParam("product") String product,
            @RequestParam("type") String type,
            Model model
    ) {
        String producto = new String(Base64.getUrlDecoder().decode(product), StandardCharsets.UTF_8);
        String tipo = new String(Base64.getUrlDecoder().decode(type), StandardCharsets.UTF_8);

        Mono<List<Repertorio>> selection1 = repertorioService.obtenerRepertorios(producto);
        List<Repertorio> productos = selection1.block();

        model.addAttribute("productos",productos);
        model.addAttribute("type",tipo);

        return "Selector-productos";
    }

    @PostMapping("selector_products")
    public String selectProduct(
            @RequestParam("producto") String producto,
            @RequestParam("detalle") String detalle,
            RedirectAttributes redirectAttributes) {


            System.out.println(producto);
            System.out.println(detalle);


//        redirectAttributes.addFlashAttribute("detalle", detalle);
//        redirectAttributes.addFlashAttribute("producto", producto);
            return "redirect:/selector";
    }


    @GetMapping("/selector")
    public String selector(
            @RequestParam("id") String id,
            @RequestParam("title") String title,
            @RequestParam("price") String price,
            @RequestParam("img") String img,
            Model model
    ) {
        Mono<List<RepertorioDetalle>> repertorio = repertorioDetalleService.getDetalles(id);
        List<RepertorioDetalle> detalles = repertorio.block();

        String[][] detallesCodificados = detalles.stream()
                .map(detalle -> new String[]{
                        Base64.getUrlEncoder().encodeToString((detalle.getProducto() + "s").getBytes(StandardCharsets.UTF_8)),
                        Base64.getUrlEncoder().encodeToString(detalle.getDetalle().getBytes(StandardCharsets.UTF_8))
                })
                .toArray(String[][]::new);


        model.addAttribute("encodes", detallesCodificados);
        model.addAttribute("rpdetalle", repertorio.block());
        model.addAttribute("id", id);
        model.addAttribute("title", title);
        model.addAttribute("price", price);
        model.addAttribute("img", img);
        return "Selector";
    }
}
