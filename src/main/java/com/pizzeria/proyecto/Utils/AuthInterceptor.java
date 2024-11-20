package com.pizzeria.proyecto.Utils;

import jakarta.servlet.http.Cookie;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // Comprobación de la autenticación (similar al filtro)
//        boolean isAuthenticated = request.getSession().getAttribute("user") != null;
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

        // Acceso a las rutas públicas sin autenticación
        if (request.getRequestURI().startsWith("/login") || request.getRequestURI().startsWith("/register")) {
            return true;
        }

        // Si no está autenticado, redirigir al login
        if (!isAuthenticated) {
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }
}
