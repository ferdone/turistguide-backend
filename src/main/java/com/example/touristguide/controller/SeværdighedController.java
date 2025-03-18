package com.example.touristguide.controller;

import com.example.touristguide.model.Seværdighed;
import com.example.touristguide.service.SeværdighedService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/attraktioner")
public class SeværdighedController {

    private final SeværdighedService sevService;

    public SeværdighedController(SeværdighedService seværdighedService) {
        this.sevService = seværdighedService;
        System.out.println("SeværdighedController startet - klar til at modtage requests");
    }

    @GetMapping
    public List<Seværdighed> getAllAttractions() {
        System.out.println("GET request modtaget for at hente alle attraktioner");
        return sevService.findAll();
    }

    @GetMapping("/{navn}")
    public Optional<Seværdighed> getAttractionByName(@PathVariable String navn) {
        System.out.println("GET request modtaget for: " + navn);
        return sevService.findByNavn(navn);
    }

    @PostMapping
    public Seværdighed createAttraction(@RequestBody Seværdighed attraktion) {
        System.out.println("POST request modtaget for at oprette: " + attraktion.getNavn());
        return sevService.addAttraktion(attraktion);
    }

    @PutMapping("/{navn}")
    public Seværdighed updateSight(@PathVariable String navn, @RequestBody Seværdighed updatedAttraktion) {
        System.out.println("PUT request modtaget for at opdatere: " + navn);
        
        Seværdighed result = sevService.updateAttraktion(navn, updatedAttraktion);
        if (result == null) {
            System.out.println("Advarsel: " + navn + " blev ikke fundet!");
        }
        
        return result;
    }

    @DeleteMapping("/{navn}")
    public void sletAttraction(@PathVariable String navn) {
        System.out.println("DELETE request modtaget for: " + navn);
        sevService.deleteAttraktion(navn);
    }
} 