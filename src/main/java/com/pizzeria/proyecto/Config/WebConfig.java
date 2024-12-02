package com.pizzeria.proyecto.Config;

import com.pizzeria.proyecto.Utils.ApiClientStatus;
import com.pizzeria.proyecto.Utils.AuthInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final ApiClientStatus apiClientStatus;

    public WebConfig(AuthInterceptor authInterceptor, ApiClientStatus apiClientStatus) {
        this.authInterceptor = authInterceptor;
        this.apiClientStatus = apiClientStatus;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/user/**","/car/**")
                .excludePathPatterns(
                        "/oferts",
                        "/pizzas",
                        "/drinks",
                        "/single",
                        "/combos",
                        "/snacks",
                        "/unbeatables"
                );
        registry.addInterceptor(apiClientStatus)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/js/**", "/images/**", "/webjars/**");
    }
}
