package com.example.mealcalendar.bean;

import java.util.ArrayList;
import java.util.List;

public class DayBean {

    private Integer giorno;
    private List<MealBean> pasti = new ArrayList<>();


    public void setGiorno(Integer g) {
        if (g == null || g <= 0) {
            throw new IllegalArgumentException("Il giorno deve essere un intero positivo.");
        }
        this.giorno = g;
    }

    // Aggiunge un pasto solo se non nullo e se ha dati validi
    public void aggiungiPasto(MealBean m) {

        if (m == null) {
            throw new IllegalArgumentException("Il pasto non può essere nullo.");
        }
        if (m.getNome() == null || m.getNome().isEmpty()) {
            throw new IllegalArgumentException("Il nome del pasto non può essere vuoto.");
        }
        if (m.getKcal() < 0) {
            throw new IllegalArgumentException("Le kcal devono essere un numero positivo o zero.");
        }
        // Puoi aggiungere altri controlli se vuoi, es. su descrizione

        pasti.add(m);
    }

    public List<MealBean> getPasti() {
        return pasti;
    }
}

