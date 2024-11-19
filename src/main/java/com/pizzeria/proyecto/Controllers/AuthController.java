package com.pizzeria.proyecto.Controllers;

import com.pizzeria.proyecto.Models.LoginRequest;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    private final WebClient webClient;

    @PostMapping
    public String login(@ModelAttribute LoginRequest loginRequest, Model model) {
        try {
            webClient.post()
                    .uri("login")
                    .bodyValue(loginRequest)
                    .exchangeToMono(response -> {
                        // Accede a las cabeceras de la respuesta, incluyendo las cookies en `Set-Cookie`
                        List<String> cookies = response.headers().header("Set-Cookie");

                        if (cookies != null) {
                            // Itera sobre las cookies y busca los valores de `access_token` y `refresh_token`
                            cookies.forEach(cookie -> {
                                System.out.println("Cookie completa: " + cookie);

                                // Puedes extraer el valor de cada cookie usando una verificación simple
                                if (cookie.contains("access_token")) {
                                    String accessToken = extraerValorCookie(cookie, "access_token");
                                    System.out.println("Access Token: " + accessToken);
                                }
                                if (cookie.contains("refresh_token")) {
                                    String refreshToken = extraerValorCookie(cookie, "refresh_token");
                                    System.out.println("Refresh Token: " + refreshToken);
                                }
                            });
                        }

                        return response.bodyToMono(String.class);
                    })
//                    .doOnError(error -> System.err.println("Error en la solicitud: " + error))
                    .subscribe();

        } catch (Exception e) {
            System.out.println("Excepción: " + e.getMessage());
        }
//        String loginResponse = String.valueOf(response.block());
//
//        if (loginResponse != null && loginResponse.contains("inicio de sesión exitoso")) {
//            // Establecer las cookies
//            Cookie accessTokenCookie = new Cookie("access_token", "someAccessTokenValue");
//            accessTokenCookie.setHttpOnly(true);
//            accessTokenCookie.setSecure(true);  // Si usas HTTPS
//            accessTokenCookie.setPath("/");
//            accessTokenCookie.setMaxAge(60 * 60); // 1 hora de duración
//
//            Cookie refreshTokenCookie = new Cookie("refresh_token", "someRefreshTokenValue");
//            refreshTokenCookie.setHttpOnly(true);
//            refreshTokenCookie.setSecure(true);  // Si usas HTTPS
//            refreshTokenCookie.setPath("/");
//            refreshTokenCookie.setMaxAge(60 * 60 * 24 * 30); // 30 días de duración
//
//            response.addCookie(accessTokenCookie);
//            response.addCookie(refreshTokenCookie);
//
//            return "redirect:/user";
//        } else {
//            model.addAttribute("error", "Inicio de sesión fallido. Verifique sus credenciales.");
//            return "redirect:/login";
//        }
        return "login";
    }




    private String extraerValorCookie(String cookie, String nombre) {
        String[] partes = cookie.split(";");
        for (String parte : partes) {
            if (parte.trim().startsWith(nombre + "=")) {
                return parte.split("=")[1];
            }
        }
        return null;
    }
}
