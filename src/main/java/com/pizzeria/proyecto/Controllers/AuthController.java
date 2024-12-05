package com.pizzeria.proyecto.Controllers;

import com.pizzeria.proyecto.Models.Cliente;
import com.pizzeria.proyecto.Models.LoginRequest;
import com.pizzeria.proyecto.Service.ClienteService;
import com.pizzeria.proyecto.Service.SesionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {
    private final SesionService sesionService;
    private final ClienteService clienteService;

    public static final String id = "id";
    public static final String username = "username";
    public static final String correo = "correo";
    public static final String telefono = "telefono";
    public static final String contrasena = "contrasena";

    @PostMapping("login")
    public String login(@ModelAttribute LoginRequest loginRequest, @RequestParam(required = false) String redirect, @RequestParam(required = false) String content, Model model, HttpServletResponse httpServletResponse, HttpSession session) {
        if (null != loginRequest) {
            if (sesionService.Login(loginRequest, httpServletResponse).equals(true)) {
                session.setAttribute(id, clienteService.getCliente(loginRequest.getUsuario()).block().getId_cliente());
                session.setAttribute(username, loginRequest.getUsuario());
                session.setAttribute(correo, clienteService.getCliente(loginRequest.getUsuario()).block().getCorreo());
                session.setAttribute(telefono, clienteService.getCliente(loginRequest.getUsuario()).block().getTelefono());
                session.setAttribute(contrasena, loginRequest.getContrasena());

                if(content!=null) {
                    String decode = (new String(Base64.getUrlDecoder().decode(content), StandardCharsets.UTF_8));
//                    System.out.println("en el login: "+decode);
                    return "redirect:/" + redirect+"?"+decode;
                }

                return "redirect:/";
            } else {
                model.addAttribute("error", "Credenciales incorrectas");
                model.addAttribute(username, loginRequest.getUsuario());
                model.addAttribute(contrasena, loginRequest.getContrasena());
                if (redirect!=null&&content!=null){
                    model.addAttribute("redirect",redirect);
                    model.addAttribute("content",content);
                }
                return "login";
            }
        } else {
            model.addAttribute("error", "Fallo en el ingreso de datos");
            model.addAttribute(username, loginRequest.getUsuario());
            model.addAttribute(contrasena, loginRequest.getContrasena());
            if (redirect!=null&&content!=null){
                model.addAttribute("redirect",redirect);
                model.addAttribute("content",content);
            }
            return "login";
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
    public String registerPage(HttpServletRequest request, Model model, @RequestParam(required = false) String redirect, @RequestParam(required = false) String content) {
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
        if (redirect!=null&&content!=null){
            model.addAttribute("redirect",redirect);
            model.addAttribute("content",content);
        }
        return isAuthenticated? "redirect:/user" : "register";
    }

    @PostMapping("register")
    public String register(@ModelAttribute Cliente cliente, Model model,  HttpServletResponse response, HttpSession session, @RequestParam(required = false) String redirect, @RequestParam(required = false) String content) {
        if (cliente != null) {
            String result = sesionService.Register(cliente, response);
            if (result.equals("hecho")) {
                session.setAttribute("id", clienteService.getCliente(cliente.getUsuario().toString()).block().getId_cliente());
                session.setAttribute("username", cliente.getUsuario());
                session.setAttribute("correo", cliente.getCorreo());
                session.setAttribute("telefono", cliente.getTelefono());
                session.setAttribute(contrasena, cliente.getContrasena());

                if(content!=null) {
                    String decode = (new String(Base64.getUrlDecoder().decode(content), StandardCharsets.UTF_8));
                    return "redirect:/" + redirect+"?"+decode;
                }

                return "redirect:/";

            } else {
                model.addAttribute("error", result);
                model.addAttribute("username", cliente.getUsuario());
                model.addAttribute("correo", cliente.getCorreo());
                model.addAttribute("telefono", cliente.getTelefono());
                model.addAttribute(contrasena, cliente.getContrasena());
                if (redirect!=null&&content!=null){
                    model.addAttribute("redirect",redirect);
                    model.addAttribute("content",content);
                }
                return "Register";
            }

        } else {
            model.addAttribute("error", "Fallo en el envío de datos");
            model.addAttribute("username", cliente.getUsuario());
            model.addAttribute("correo", cliente.getCorreo());
            model.addAttribute("telefono", cliente.getTelefono());
            model.addAttribute(contrasena, cliente.getContrasena());
            if (redirect!=null&&content!=null){
                model.addAttribute("redirect",redirect);
                model.addAttribute("content",content);
            }
            return "Register";
        }
    }
}
