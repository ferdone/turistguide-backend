package com.example.touristguide.model;

import java.util.ArrayList;
import java.util.List;

// repræsenterer en bruger i systemet
public class Bruger {
    private Long id; // primær nøgle fra databasen
    private String brugernavn; // unikt brugernavn
    private String email; // email adresse (unik)
    private String adgangskode; // TODO: kryptér dette senere
    private List<Anmeldelse> anmeldelser = new ArrayList<>(); // brugerens anmeldelser

    // tom constructor til json/databinding
    public Bruger() {
        // her kunne man initialisere noget, men det er ikke nødvendigt
    }

    // constructor til at oprette nye brugere
    public Bruger(String brugernavn, String email, String adgangskode) {
        this.brugernavn = brugernavn;
        this.email = email;
        this.adgangskode = adgangskode;
        // TODO: kryptér password i en rigtig produktion
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
    
    // tilføj en anmeldelse til brugerens liste
    public void addReview(Anmeldelse anmeldelse) {
        this.anmeldelser.add(anmeldelse);
        anmeldelse.setBruger(this); // sætter referencen begge veje
    }
    
    @Override
    public String toString() {
        return "Bruger [id=" + id + ", brugernavn=" + brugernavn + ", email=" + email + "]";
    }
} 