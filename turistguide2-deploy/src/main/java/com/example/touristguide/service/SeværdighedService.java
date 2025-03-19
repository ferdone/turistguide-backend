package com.example.touristguide.service;

import com.example.touristguide.model.Seværdighed;
import com.example.touristguide.repository.SeværdighedRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeværdighedService {

    private final SeværdighedRepository sevRepo; 

    public SeværdighedService(SeværdighedRepository seværdighedRepository) {
        this.sevRepo = seværdighedRepository;
    }

    public List<Seværdighed> findAll() {
        return sevRepo.findAll();
    }

    public Optional<Seværdighed> findByNavn(String navn) {
        return sevRepo.findByNavn(navn);
    }

    public Seværdighed addAttraktion(Seværdighed attraktion) {
        return sevRepo.save(attraktion);
    }

    public Seværdighed updateAttraktion(String navn, Seværdighed updatedAttraktion) {
        Optional<Seværdighed> existingAttraktion = sevRepo.findByNavn(navn);
        
        if (existingAttraktion.isPresent()) {
            Seværdighed att = existingAttraktion.get();
            
            att.setNavn(updatedAttraktion.getNavn());
            att.setLokation(updatedAttraktion.getLokation());
            att.setBeskrivelse(updatedAttraktion.getBeskrivelse());
            
            if (updatedAttraktion.getBedømmelse() != null) {
                att.setBedømmelse(updatedAttraktion.getBedømmelse());
            }
            
            return sevRepo.save(att);
        } else {
            System.out.println("fandt ikke: " + navn);
            return null;
        }
    }

    public void deleteAttraktion(String navn) {
        sevRepo.deleteByNavn(navn);
    }
} 