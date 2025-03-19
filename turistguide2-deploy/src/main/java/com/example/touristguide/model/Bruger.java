package com.example.touristguide.model;

import java.util.ArrayList;
import java.util.List;

// repræsenterer en bruger i systemet
public class Bruger {
    private Long id; 
    private String brugernavn; 
    private String email;
    private String adgangskode;
    private List<Anmeldelse> anmeldelser = new ArrayList<>();

    // tom constructor 
    public Bruger() {
    }

    // constructor
    public Bruger(String brugernavn, String email, String adgangskode) {
        this.brugernavn = brugernavn;
        this.email = email;
        this.adgangskode = adgangskode;
    }

    // getters og setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrugernavn() {
        return brugernavn;
    }

    public void setBrugernavn(String brugernavn) {
        this.brugernavn = brugernavn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdgangskode() {
        return adgangskode;
    }

    public void setAdgangskode(String adgangskode) {
        this.adgangskode = adgangskode;
    }

    public List<Anmeldelse> getAnmeldelser() {
        return anmeldelser;
    }

    public void setAnmeldelser(List<Anmeldelse> anmeldelser) {
        this.anmeldelser = anmeldelser;
    }
    
    // anmeldelse til bruger liste
    public void addReview(Anmeldelse anmeldelse) {
        this.anmeldelser.add(anmeldelse);
        anmeldelse.setBruger(this); // sæt reference
    }
    
    @Override
    public String toString() {
        return "Bruger [id=" + id + ", brugernavn=" + brugernavn + ", email=" + email + "]";
    }
} 