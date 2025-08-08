package com.example.mealcalendar.bean;



public class UserBean {

    private String nome;
    private String email;
    private String ruolo;
    private DietBean dieta;

    public UserBean(String nome, String email, String ruolo) {
        this.nome = nome;
        this.email = email;
        this.ruolo = ruolo;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getRuolo() { return ruolo; }
    public DietBean getDieta() { return dieta; }

    public void setDieta(DietBean dieta) {
        this.dieta = dieta;
    }
}