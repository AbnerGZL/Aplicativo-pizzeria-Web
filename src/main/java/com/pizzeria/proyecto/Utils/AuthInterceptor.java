package com.pizzeria.proyecto.Utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // Verificar si el usuario está autenticado
        boolean isAuthenticated = false;

        // Verificar si los cookies de autenticación existen
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("access_token".equals(cookie.getName()) && cookie.getValue() != null) {
                    isAuthenticated = true;
                    break;
                }
            }
        }

        // Agregar encabezados para evitar la caché en rutas protegidas
        if (isAuthenticated) {
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
            response.setHeader("Pragma", "no-cache"); // HTTP 1.0
            response.setDateHeader("Expires", 0); // Proxies
        }

        // Redirigir al usuario a la ruta protegida si intenta acceder a /login o /register mientras está autenticado
        if (isAuthenticated && (request.getRequestURI().equals("/login") || request.getRequestURI().equals("/register"))) {
            response.sendRedirect("/user"); // Redirige a la ruta protegida
            return false;
        }

        // Acceso a las rutas públicas permitidas sin autenticación
        if (!isAuthenticated && (request.getRequestURI().startsWith("/login") || request.getRequestURI().startsWith("/register"))) {
            // Configura los encabezados para evitar que el navegador almacene en caché la página
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate"); // HTTP 1.1
            response.setHeader("Pragma", "no-cache"); // HTTP 1.0
            response.setDateHeader("Expires", 0); // Proxies
            return true;
        }

        // Redirigir al login si el usuario intenta acceder a rutas protegidas sin autenticación
        if (!isAuthenticated) {
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }

}
