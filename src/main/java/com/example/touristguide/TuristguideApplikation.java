package com.example.touristguide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TuristguideApplikation {
    
    public static void main(String[] args) {
        System.out.println("Starter TouristGuide app...");
        
        SpringApplication.run(TuristguideApplikation.class, args);
        
        System.out.println("App kører nu på port 8080!");
        System.out.println("Åbn browser på http://localhost:8080/api/attraktioner for at se alle");
    }
} 