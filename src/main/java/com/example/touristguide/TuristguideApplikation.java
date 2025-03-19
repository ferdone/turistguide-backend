package com.example.touristguide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class TuristguideApplikation {
    
    private static final Logger logger = LoggerFactory.getLogger(TuristguideApplikation.class);
    
    @Value("${spring.profiles.active:default}")
    private String activeProfile;
    
    public static void main(String[] args) {
        try {
            logger.info("Starting Turistguide application...");
            SpringApplication app = new SpringApplication(TuristguideApplikation.class);
            // Uncomment if you need to set a profile programmatically
            // app.setAdditionalProfiles("prod");
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
                
                logger.info("CORS configured, active profile: {}", activeProfile);
            }
        };
    }
} 