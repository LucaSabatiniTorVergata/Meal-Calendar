package com.example.mealcalendar.findrest;

public class RistoranteBean {
    private String id;
    private String nome;
    private String password;
    private int tavoliDisponibili;
    private int tavoliPrenotati;
    private boolean aperto;
    private String citta;

    public RistoranteBean() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID non può essere vuoto");
        }
        this.id = id.trim();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome non può essere vuoto");
        }
        this.nome = nome.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password almeno 6 caratteri");
        }
        this.password = password;
    }

    public int getTavoliDisponibili() {
        return tavoliDisponibili;
    }

    public void setTavoliDisponibili(int tavoliDisponibili) {
        if (tavoliDisponibili < 0) {
            throw new IllegalArgumentException("Tavoli disponibili non può essere negativo");
        }
        this.tavoliDisponibili = tavoliDisponibili;
    }

    public int getTavoliPrenotati() {
        return tavoliPrenotati;
    }

    public void setTavoliPrenotati(int tavoliPrenotati) {
        if (tavoliPrenotati < 0) {
            throw new IllegalArgumentException("Tavoli prenotati non può essere negativo");
        }
        this.tavoliPrenotati = tavoliPrenotati;
    }

    public boolean isAperto() {
        return aperto;
    }

    public void setAperto(boolean aperto) {
        this.aperto = aperto;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        if (citta == null || citta.isBlank()) {
            throw new IllegalArgumentException("La città non può essere vuota");
        }
        this.citta = citta.trim();
    }
}
