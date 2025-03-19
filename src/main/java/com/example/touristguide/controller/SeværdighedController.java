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
    }

    @GetMapping
    public List<Seværdighed> getAllAttractions() {
        return sevService.findAll();
    }

    @GetMapping("/{navn}")
    public Optional<Seværdighed> getAttractionByName(@PathVariable String navn) {
        return sevService.findByNavn(navn);
    }

    @PostMapping
    public Seværdighed createAttraction(@RequestBody Seværdighed attraktion) {
        return sevService.addAttraktion(attraktion);
    }

    @PutMapping("/{navn}")
    public Seværdighed updateSight(@PathVariable String navn, @RequestBody Seværdighed updatedAttraktion) {
        Seværdighed result = sevService.updateAttraktion(navn, updatedAttraktion);
        return result;
    }

    @DeleteMapping("/{navn}")
    public void sletAttraction(@PathVariable String navn) {
        sevService.deleteAttraktion(navn);
    }
} 