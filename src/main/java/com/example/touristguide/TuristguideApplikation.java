package com.example.touristguide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class TuristguideApplikation {
    
    private static final Logger logger = LoggerFactory.getLogger(TuristguideApplikation.class);
    
    public static void main(String[] args) {
        try {
            logger.info("Starting Turistguide application...");
            SpringApplication app = new SpringApplication(TuristguideApplikation.class);
            app.run(args);
            logger.info("Turistguide application started successfully");
        } catch (Exception e) {
            logger.error("Failed to start Turistguide application", e);
            throw e;
        }
    }
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*");
            }
        };
    }
} 