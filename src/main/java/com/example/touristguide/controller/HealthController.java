package com.example.touristguide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {

    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public Map<String, Object> healthCheck() {
        logger.info("Health check endpoint called");
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Application is running");
        
        try {
            // Simple database connectivity check
            String dbStatus = jdbcTemplate.queryForObject("SELECT 'UP' as status", String.class);
            response.put("database", dbStatus);
            logger.info("Database connection successful");
        } catch (Exception e) {
            logger.error("Database connection failed", e);
            response.put("database", "DOWN");
            response.put("error", e.getMessage());
        }
        
        return response;
    }
    
    @GetMapping("/info")
    public Map<String, Object> getInfo() {
        logger.info("Info endpoint called");
        Map<String, Object> info = new HashMap<>();
        info.put("app", "Turistguide Backend");
        info.put("version", "1.0");
        info.put("java", System.getProperty("java.version"));
        info.put("javaHome", System.getProperty("java.home"));
        
        Map<String, String> env = new HashMap<>();
        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
            if (entry.getKey().startsWith("SPRING_") || entry.getKey().startsWith("AZURE_")) {
                // Mask passwords
                if (entry.getKey().toLowerCase().contains("password")) {
                    env.put(entry.getKey(), "********");
                } else {
                    env.put(entry.getKey(), entry.getValue());
                }
            }
        }
        info.put("env", env);
        
        return info;
    }
} 