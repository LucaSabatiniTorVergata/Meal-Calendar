package com.example.mealcalendar.model;

import java.util.Objects;

public class UserEntity {

    private String nome;
    private String email;
    private String ruolo;
    private DietEntity dietaAssegnata;  // può essere null

    public UserEntity(String nome, String email, String ruolo) {
        this.nome = nome;
        this.email = email;
        this.ruolo = ruolo;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getRuolo() { return ruolo; }
    public DietEntity getDietaAssegnata() { return dietaAssegnata; }

    public void setDietaAssegnata(DietEntity dieta) {
        this.dietaAssegnata = dieta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}

