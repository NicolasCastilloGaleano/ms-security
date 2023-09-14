package com.mssecurity.mssecurity.Configurations;

import com.mssecurity.mssecurity.interceptors.SecurityInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("security/**");
                // Asegúrate de que las rutas sean las correctas
    }
}
