package com.example.mealcalendar.bean;

public class UserBean {

    private String nome;
    private String email;
    private String ruolo;
    private DietBean dieta;

    public UserBean(String nome, String email, String ruolo) {
        setNome(nome);
        setEmail(email);
        setRuolo(ruolo);
    }

    public void setDieta(DietBean dieta) {
        if (dieta == null) {
            throw new IllegalArgumentException("La dieta non può essere nulla.");
        }
        this.dieta = dieta;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome non può essere nullo o vuoto.");
        }
        this.nome = nome;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("L'email non può essere nulla o vuota.");
        }
        this.email = email;
    }

    public void setRuolo(String ruolo) {
        if (ruolo == null || ruolo.isBlank()) {
            throw new IllegalArgumentException("Il ruolo non può essere nullo o vuoto.");
        }
        this.ruolo = ruolo;
    }

    public DietBean getDieta() {
        return dieta;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getRuolo() {
        return ruolo;
    }

}
