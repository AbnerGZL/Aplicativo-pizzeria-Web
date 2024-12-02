package com.pizzeria.proyecto.Repositories;

import com.pizzeria.proyecto.Models.Cliente;
import com.pizzeria.proyecto.Models.LoginRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Repository
@RequiredArgsConstructor
public class SesionRepository {

    private final WebClient webClient;

    public Boolean postLogin(LoginRequest loginRequest, HttpServletResponse httpServletResponse){
        AtomicReference<String> accessToken = new AtomicReference<>();
        AtomicReference<String> refreshToken = new AtomicReference<>();
        try {
            webClient.post()
                .uri("login")
                .bodyValue(loginRequest)
                .exchangeToMono(response -> {
                    // Obtener las cabeceras de respuesta, incluyendo las cookies en `Set-Cookie`
                    List<String> cookies = response. headers().header("Set-Cookie");

                    if (cookies != null) {
                        cookies.forEach(cookie -> {
//                            System.out.println("Cookie completa: " + cookie);

                            if (cookie.contains("access_token")) {
                                accessToken.set(extraerValorCookie(cookie, "access_token"));
//                                        System.out.println("Access Token: " + accessToken);
                            }
                            else if (cookie.contains("refresh_token")) {
                                refreshToken.set(extraerValorCookie(cookie, "refresh_token"));
//                                        System.out.println("Refresh Token: " + refreshToken);
                            }
                        });
                    }

                    return response.bodyToMono(String.class);
                })
                .block();

            // Verifica que los tokens estén presentes
            if (accessToken.get() != null && refreshToken.get() != null) {
                // Establece las cookies en la respuesta HTTP
                Cookie accessTokenCookie = new Cookie("access_token", accessToken.get());
                Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken.get());

                // Opcional: Configura las cookies como HttpOnly y con ruta
                accessTokenCookie.setHttpOnly(true);
                refreshTokenCookie.setHttpOnly(true);
                accessTokenCookie.setSecure(true);
                refreshTokenCookie.setSecure(true);
                accessTokenCookie.setPath("/");
                refreshTokenCookie.setPath("/");

                // Añadir las cookies a la respuesta
                httpServletResponse.addCookie(accessTokenCookie);
                httpServletResponse.addCookie(refreshTokenCookie);

                return true;
            }

            return false;

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return false;
        }
    }

    public Boolean postRegister(Cliente cliente, HttpServletResponse httpServletResponse){
        AtomicReference<String> accessToken = new AtomicReference<>();
        AtomicReference<String> refreshToken = new AtomicReference<>();
        try {
            webClient.post()
                    .uri("registro")
                    .bodyValue(cliente)
                    .exchangeToMono(response -> {
                        // Obtener las cabeceras de respuesta, incluyendo las cookies en `Set-Cookie`
                        List<String> cookies = response.headers().header("Set-Cookie");

                        if (cookies != null) {
                            cookies.forEach(cookie -> {
//                            System.out.println("Cookie completa: " + cookie);

                                if (cookie.contains("access_token")) {
                                    accessToken.set(extraerValorCookie(cookie, "access_token"));
//                                        System.out.println("Access Token: " + accessToken);
                                }
                                else if (cookie.contains("refresh_token")) {
                                    refreshToken.set(extraerValorCookie(cookie, "refresh_token"));
//                                        System.out.println("Refresh Token: " + refreshToken);
                                }
                            });
                        }

                        return response.bodyToMono(String.class);
                    })
                    .block();

            // Verifica que los tokens estén presentes
            if (accessToken.get() != null && refreshToken.get() != null) {
                // Establece las cookies en la respuesta HTTP
                Cookie accessTokenCookie = new Cookie("access_token", accessToken.get());
                Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken.get());

                // Opcional: Configura las cookies como HttpOnly y con ruta
                accessTokenCookie.setHttpOnly(true);
                accessTokenCookie.setSecure(true);
                refreshTokenCookie.setHttpOnly(true);
                refreshTokenCookie.setSecure(true);
                accessTokenCookie.setPath("/");
                refreshTokenCookie.setPath("/");

                // Añadir las cookies a la respuesta
                httpServletResponse.addCookie(accessTokenCookie);
                httpServletResponse.addCookie(refreshTokenCookie);

                return true;
            }

            return false;

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return false;
        }
    }

    public static String extraerValorCookie(String cookie, String nombre) {
        String[] partes = cookie.split(";");
        for (String parte : partes) {
            if (parte.trim().startsWith(nombre + "=")) {
                return parte.split("=")[1];
            }
        }
        return null;
    }
}
