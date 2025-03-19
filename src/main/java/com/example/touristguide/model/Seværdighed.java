package com.example.touristguide.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Seværdighed {

    private Long id;
    private String navn;
    private String lokation;
    private String beskrivelse;
    private BigDecimal bedømmelse;
    
    private List<Anmeldelse> anmeldelser = new ArrayList<>();

    public Seværdighed() {
    }

    // ny seværdighed
    public Seværdighed(String navn, String lokation, String beskrivelse) {
        this.navn = navn;
        this.lokation = lokation;
        this.beskrivelse = beskrivelse;
        this.bedømmelse = BigDecimal.ZERO; 
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getLokation() {
        return lokation;
    }

    public void setLokation(String lokation) {
        this.lokation = lokation;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public BigDecimal getBedømmelse() {
        return bedømmelse;
    }

    public void setBedømmelse(BigDecimal bedømmelse) {
        this.bedømmelse = bedømmelse;
    }

    public List<Anmeldelse> getAnmeldelser() {
        return anmeldelser;
    }

    public void setAnmeldelser(List<Anmeldelse> anmeldelser) {
        this.anmeldelser = anmeldelser;
    }
    
    @Override
    public String toString() {
        return "Seværdighed [id=" + id + ", navn=" + navn + ", lokation=" + lokation + "]";
    }
}