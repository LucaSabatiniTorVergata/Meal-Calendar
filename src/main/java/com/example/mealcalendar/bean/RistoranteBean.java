package com.example.mealcalendar.bean;

import com.example.mealcalendar.model.TipologiaRistorante;

public class RistoranteBean {

    private String username;
    private String email;
    private String password;
    private String ruolo;
    private TipologiaRistorante tipologiaRistorante;

    public RistoranteBean(String username, String email, String password, String ruolo, TipologiaRistorante tipologiaRistorante) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
        this.tipologiaRistorante = tipologiaRistorante;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }

    public TipologiaRistorante getTipologiaRistorante() { return tipologiaRistorante; }
    public void setTipologiaRistorante(TipologiaRistorante tipologiaRistorante) { this.tipologiaRistorante = tipologiaRistorante; }
}
