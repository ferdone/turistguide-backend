package com.example.touristguide.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {
    
    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);
    
    @GetMapping("/")
    public Map<String, String> home() {
        logger.info("Health check endpoint called");
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Turistguide API er klar");
        return response;
    }
    
    @GetMapping("/health")
    public Map<String, String> healthCheck() {
        logger.info("Health check endpoint called");
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        return status;
    }
    
    @GetMapping("/ping")
    public Map<String, String> ping() {
        logger.info("Ping endpoint called");
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "pong");
        return response;
    }
} 