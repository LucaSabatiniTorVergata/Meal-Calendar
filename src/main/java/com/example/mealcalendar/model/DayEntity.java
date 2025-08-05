package com.example.mealcalendar.model;

import java.util.ArrayList;
import java.util.List;

public class DayEntity {

    private int giorno;
    private List<MealEntity> pasti = new ArrayList<>();

    public DayEntity(int giorno) {
        this.giorno = giorno;
    }

    public int getGiorno() {
        return giorno;
    }

    public List<MealEntity> getPasti() {
        return pasti;
    }

    public void addMeal(MealEntity pasto) {
        if (pasto != null) {
            pasti.add(pasto);
        }
    }

    public void setPasti(List<MealEntity> pasti) {
        this.pasti = pasti;
    }

    public void setGiorno(int giorno) {
        this.giorno = giorno;
    }
}



