package com.pizzeria.proyecto.Controllers;

import com.pizzeria.proyecto.Models.*;
import com.pizzeria.proyecto.Service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final RepertorioService repertorioService;
    private final RepertorioDetalleService repertorioDetalleService;
    private final CarritoService carritoService;
    private final ProventaService proventaService;
    private final PaqueteService paqueteService;
    private final ProprimaService proprimaService;

    @GetMapping
    public String index(Model model, HttpServletRequest request) {
        String locate = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("locate".equals(cookie.getName()) && cookie.getValue() != null) {
                    locate = (new String(Base64.getUrlDecoder().decode(cookie.getValue()), StandardCharsets.UTF_8)).trim().replace("%20"," ");
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

    @GetMapping("{categoria}")
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
        model.addAttribute("category", categoria);

        return "Repertory";
    }

    @PostMapping("locate")
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
            @RequestParam("id") String id,
            @RequestParam("title") String title,
            @RequestParam("price") String price,
            @RequestParam("img") String img,
            @RequestParam("category") String category,
            Model model
    ) {
        String producto = new String(Base64.getUrlDecoder().decode(product), StandardCharsets.UTF_8);
        String tipo = new String(Base64.getUrlDecoder().decode(type), StandardCharsets.UTF_8);

        List<Repertorio> productos = repertorioService.obtenerRepertorios(producto).block();
        List<Proprima> proprimas = proprimaService.get().block();
        List<RepertorioDetalle> detalles = repertorioDetalleService.get().block();

        model.addAttribute("productos",productos);
        model.addAttribute("proprimas",proprimas);
        model.addAttribute("detalles",detalles);
        model.addAttribute("type",tipo);
        model.addAttribute("id",id);
        model.addAttribute("title",title);
        model.addAttribute("price",price);
        model.addAttribute("img",img);
        model.addAttribute("category",category);

        return "Selector-productos";
    }


    @GetMapping("selector")
    public String selector(
            @RequestParam("id") Integer id,
            @RequestParam("title") String title,
            @RequestParam("price") String price,
            @RequestParam("img") String img,
            @RequestParam("category") String category,
            @RequestParam(name = "prima", required = false) String prima,
            Model model
    ) {

        List<RepertorioDetalle> detalles = repertorioDetalleService.getDetalles(id).block();
        try {
            String idProprima = detalles.getFirst().getId_proprima();
            if (idProprima != null) {
                Proprima proprima = proprimaService.getProprimaById(idProprima).block();
                model.addAttribute("proprima", proprima);
            } else{
                model.addAttribute("rpdetalle", detalles);
            }
        }catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "No es posible mostrar una selección para esta oferta");
        }
        String[][] detallesCodificados = detalles.stream()
                .map(detalle -> new String[]{
                        Base64.getUrlEncoder().encodeToString((detalle.getProducto() + "s").getBytes(StandardCharsets.UTF_8)),
                        Base64.getUrlEncoder().encodeToString(detalle.getDetalle().getBytes(StandardCharsets.UTF_8))
                })
                .toArray(String[][]::new);


        model.addAttribute("encodes", detallesCodificados);
        model.addAttribute("id", id);
        model.addAttribute("title", title);
        model.addAttribute("price", price);
        model.addAttribute("img", img);
        model.addAttribute("category", category);
        return "Selector";
    }

    @GetMapping("selection")
    public String proventaSelect(@RequestParam Map<String, String> parametros, HttpSession sesion, Model model){
        String idCliente = (String) sesion.getAttribute("id");
            parametros.forEach((clave, valor) -> {
                System.out.println("Clave: " + clave + ", Valor: " + valor);
            });


        if (idCliente != null && parametros.get("repertory") != null) {
            try{
//                //Crear el nuevo registro producto venta
//                Proventa proventa = new Proventa(null, parametros.get("id"),null,"carrito");
//                proventaService.postProventa(proventa);
//
//                //Crear el nuevo registro carrito en base al producto venta ingresado
//                String idproventa = Objects.requireNonNull(proventaService.getProventa(parametros.get("id")).block()).getId_proventa();
//                Carrito carrito = new Carrito(null, null, idCliente,idproventa);
//                carritoService.postCarrito(carrito);
//
//                //Crear los paquetes pertenecientes al producto venta ingresado
//
//                List<RepertorioDetalle> detalles = repertorioDetalleService.getDetalles(parametros.get("id")).block();
//                parametros.forEach((clave, valor) -> {
//                    proprimaService.getProprima(valor,"");
//                    System.out.println("Clave: " + clave + ", Valor: " + valor);
//                });
//                Paquete paquete = new Paquete(null,idproventa,null,null);
//                paqueteService.postPaquete(paquete);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "redirect:/car";
        }
        // Convertir los parámetros para la redirección
        String redirectQuery = parametros.entrySet()
                .stream()
                .map(entry ->
                        URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "=" +
                        URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                .reduce((param1, param2) -> param1 + "&" + param2)
                .orElse("");

        String encodedcontent = Base64.getUrlEncoder().encodeToString(redirectQuery.getBytes(StandardCharsets.UTF_8));

        model.addAttribute("error", "inicie sesión antes de agregar su pedido al carrito");
        model.addAttribute("redirect", "selection");
        model.addAttribute("content", encodedcontent);
        return "login";
    }
}
