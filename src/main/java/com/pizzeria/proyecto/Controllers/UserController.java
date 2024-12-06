package com.pizzeria.proyecto.Controllers;

import com.pizzeria.proyecto.Models.Cliente;
import com.pizzeria.proyecto.Service.ClienteService;
import com.pizzeria.proyecto.Service.PedidoService;
import com.pizzeria.proyecto.Service.SesionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final ClienteService clienteService;
    private final PedidoService pedidoService;

    @GetMapping
    public String details(Model model) {
        return "user/details";
    }

    @GetMapping("/history")
    public String history() {
        return "user/history";
    }

    @GetMapping("/active")
    public String active() {
        return "user/actives";
    }

    @GetMapping("/devolutions")
    public String devolutions() {
        return "user/devolutions";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Cliente cliente, Model model, HttpSession session) {
        if (cliente.getId_cliente() != null && clienteService.editCliente(cliente)) {
            Class<?> clase = cliente.getClass();
            Field[] atributos = clase.getDeclaredFields();
            for (Field f : atributos) {
                session.removeAttribute("'"+f.getName()+"'");
            }
            session.setAttribute("id", cliente.getId_cliente());
            session.setAttribute("username", cliente.getUsuario());
            session.setAttribute("correo", cliente.getCorreo());
            session.setAttribute("telefono", cliente.getTelefono());
            session.setAttribute("contrasena", cliente.getContrasena());

            return "redirect:/user";
        }
        model.addAttribute("error", "Hubo un error al procesar sus datos");
        return "redirect:/user";
    }

}
