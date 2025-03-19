package com.example.touristguide.model;

import java.time.LocalDateTime;

// klasse til anmeldelser
public class Anmeldelse {
    // disse felter matcher 
    private Long id;
    private Long brugerId;
    private Long seværdighedId;
    private Integer bedømmelse; 
    private String kommentar;
    private LocalDateTime oprettetDato;
    
    // objektreferencer 
    private Bruger bruger;
    private Seværdighed seværdighed; 

    // tom constructor 
    public Anmeldelse() {
    }

    // constructor 
    public Anmeldelse(Long brugerId, Long seværdighedId, Integer bedømmelse, String kommentar) {
        this.brugerId = brugerId;
        this.seværdighedId = seværdighedId;
        this.bedømmelse = bedømmelse;
        this.kommentar = kommentar;
        this.oprettetDato = LocalDateTime.now(); // automatisk tidsstempel
    }

    // getter og setter 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return brugerId;
    }

    public void setUserId(Long brugerId) {
        this.brugerId = brugerId;
    }

    public Long getSeværdighedId() {
        return seværdighedId;
    }

    public void setSeværdighedId(Long seværdighedId) {
        this.seværdighedId = seværdighedId;
    }

    public Integer getBedømmelse() {
        return bedømmelse;
    }

    public void setBedømmelse(Integer bedømmelse) {
        // sikrer at bedømmelse er mellem 1 og 5
        if (bedømmelse < 1) {
            this.bedømmelse = 1;
        } else if (bedømmelse > 5) {
            this.bedømmelse = 5;
        } else {
            this.bedømmelse = bedømmelse;
        }
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    public LocalDateTime getOprettetDato() {
        return oprettetDato;
    }

    public void setOprettetDato(LocalDateTime oprettetDato) {
        this.oprettetDato = oprettetDato;
    }

    public Bruger getBruger() {
        return bruger;
    }

    public void setBruger(Bruger bruger) {
        this.bruger = bruger;
    }

    public Seværdighed getSeværdighed() {
        return seværdighed;
    }

    public void setSeværdighed(Seværdighed seværdighed) {
        this.seværdighed = seværdighed;
    }
    
    @Override
    public String toString() {
        return "Anmeldelse [id=" + id + ", bedømmelse=" + bedømmelse + ", bruger=" 
               + (bruger != null ? bruger.getBrugernavn() : "null") + "]";
    }
} 