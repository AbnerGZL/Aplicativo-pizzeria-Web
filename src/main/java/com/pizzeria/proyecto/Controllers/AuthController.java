package com.pizzeria.proyecto.Controllers;

import com.pizzeria.proyecto.Models.Cliente;
import com.pizzeria.proyecto.Models.LoginRequest;
import com.pizzeria.proyecto.Service.ClienteService;
import com.pizzeria.proyecto.Service.SesionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {
    private final SesionService sesionService;
    private final ClienteService clienteService;

    @PostMapping("login")
    public String login(@ModelAttribute LoginRequest loginRequest, Model model, HttpServletResponse httpServletResponse, HttpSession session) {
        if (loginRequest != null) {
            if (sesionService.Login(loginRequest, httpServletResponse).equals(true)) {
                session.setAttribute("id", clienteService.getCliente(loginRequest.getUsuario().toString()).block().getId_cliente());
                session.setAttribute("username", loginRequest.getUsuario());
                session.setAttribute("correo", clienteService.getCliente(loginRequest.getUsuario().toString()).block().getCorreo());
                session.setAttribute("telefono", clienteService.getCliente(loginRequest.getUsuario().toString()).block().getTelefono());
                session.setAttribute("contrasena", loginRequest.getContrasena());

                return "redirect:/user";
            } else {
                model.addAttribute("error", "Credenciales incorrectas");
                model.addAttribute("username", loginRequest.getUsuario());
                model.addAttribute("contrasena", loginRequest.getContrasena());
                return "Login";
            }
        } else {
            model.addAttribute("error", "Fallo en el ingreso de datos");
            model.addAttribute("username", loginRequest.getUsuario());
            model.addAttribute("contrasena", loginRequest.getContrasena());
            return "Login";
        }
    }

    @GetMapping("logout")
    public String logout(HttpServletResponse response, HttpSession session) {
        if (sesionService.Logout(response).equals(true)){
            session.invalidate();
            return "redirect:/login?success=true";
        } else {
            return "redirect:/login?error=true";
        }
    }

    @GetMapping("login")
    public String loginPage(HttpServletRequest request) {
        boolean isAuthenticated = false;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("access_token".equals(cookie.getName()) && cookie.getValue() != null) {
                    isAuthenticated = true;
                    break;
                }
            }
        }
        return isAuthenticated? "redirect:/user" : "login";
    }

    @GetMapping("register")
    public String registerPage(HttpServletRequest request) {
        boolean isAuthenticated = false;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("access_token".equals(cookie.getName()) && cookie.getValue() != null) {
                    isAuthenticated = true;
                    break;
                }
            }
        }

        return isAuthenticated? "redirect:/user" : "register";
    }

    @PostMapping("register")
    public String register(@ModelAttribute Cliente cliente, Model model,  HttpServletResponse response, HttpSession session) {
        if (cliente != null) {
            String result = sesionService.Register(cliente, response);
            if (result.equals("hecho")) {
                System.out.println("estacion 1");
                session.setAttribute("id", clienteService.getCliente(cliente.getUsuario().toString()).block().getId_cliente());
                session.setAttribute("username", cliente.getUsuario());
                session.setAttribute("correo", cliente.getCorreo());
                session.setAttribute("telefono", cliente.getTelefono());
                session.setAttribute("contrasena", cliente.getContrasena());

                return "redirect:/user";

            } else {
                System.out.println("estacion 2");
                model.addAttribute("error", result);
                model.addAttribute("username", cliente.getUsuario());
                model.addAttribute("correo", cliente.getCorreo());
                model.addAttribute("telefono", cliente.getTelefono());
                model.addAttribute("contrasena", cliente.getContrasena());
                return "Register";
            }

        } else {
            System.out.println("estacion 3");
            model.addAttribute("error", "Fallo en el envío de datos");
            model.addAttribute("username", cliente.getUsuario());
            model.addAttribute("correo", cliente.getCorreo());
            model.addAttribute("telefono", cliente.getTelefono());
            model.addAttribute("contrasena", cliente.getContrasena());
            return "Register";
        }
    }
}
