package com.pizzeria.proyecto.Service;

import com.pizzeria.proyecto.Models.Cliente;
import com.pizzeria.proyecto.Models.LoginRequest;
import com.pizzeria.proyecto.Repositories.SesionRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SesionService extends SesionRepository {

    public SesionService(WebClient webClient) {
        super(webClient);
    }

    public Boolean Login(LoginRequest loginRequest, HttpServletResponse httpServletResponse) {

        // Lógica de negocio

        if (postLogin(loginRequest, httpServletResponse)){
            
            return true;
        } else {
            return false;
        }
    }

    public Boolean Logout(HttpServletResponse response) {
        try {
            Cookie accessTokenCookie = new Cookie("access_token", null);
            accessTokenCookie.setMaxAge(0);
            accessTokenCookie.setPath("/");

            Cookie refreshTokenCookie = new Cookie("refresh_token", null);
            refreshTokenCookie.setMaxAge(0);
            refreshTokenCookie.setPath("/");

            response.addCookie(accessTokenCookie);
            response.addCookie(refreshTokenCookie);

            return true;

        } catch(Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return false;
        }
    }

    public Boolean Register(Cliente cliente, HttpServletResponse response){

        // Logica de negocio

        postRegister(cliente, response);
        return true;
    }
}
