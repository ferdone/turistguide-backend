package com.example.touristguide.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping
    public Map<String, String> home() {
        logger.info("Home endpoint called");
        Map<String, String> response = new HashMap<>();
        response.put("status", "running");
        response.put("message", "Turistguide API is running");
        response.put("version", "1.0");
        return response;
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