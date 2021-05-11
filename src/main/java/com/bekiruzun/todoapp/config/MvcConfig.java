package com.bekiruzun.todoapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Value("${springdoc.swagger-ui.custom}")
    private boolean customSwagger;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if(customSwagger)
            registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/swagger-ui.html");
        else
            registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}