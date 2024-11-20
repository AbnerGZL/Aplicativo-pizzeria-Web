package com.pizzeria.proyecto.Config;

import com.pizzeria.proyecto.Utils.AuthInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    public WebConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns(
                        "/login",
                        "/logout",
                        "/register",
                        "/oferts",
                        "/pizzas",
                        "/drinks",
                        "/single",
                        "/combos",
                        "/snacks",
                        "/unbeatables"
                );
    }
}
