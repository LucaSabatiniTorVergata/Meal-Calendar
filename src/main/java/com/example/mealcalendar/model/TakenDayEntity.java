package com.example.mealcalendar.model;

import java.util.ArrayList;
import java.util.List;

public class TakenDayEntity {
    private int giorno;
    private List<TakenMealEntity> pasti = new ArrayList<>();

    public TakenDayEntity(int giorno) {
        this.giorno = giorno;
    }

    public int getGiorno() {
        return giorno;
    }

    public List<TakenMealEntity> getPasti() {
        return pasti;
    }

    public void addPasto(TakenMealEntity pasto) {
        pasti.add(pasto);
    }
}
