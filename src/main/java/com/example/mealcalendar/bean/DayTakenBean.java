package com.example.mealcalendar.bean;

import java.util.ArrayList;
import java.util.List;

public class DayTakenBean {
    private int giorno;
    private List<MealTakenBean> pastiAssunti=new ArrayList<>();

    public int getGiorno() {
        return giorno;
    }

    public void setGiorno(int giorno) {
        if (giorno < 1) {
            throw new IllegalArgumentException("Il numero del giorno deve essere positivo e maggiore di zero.");
        }
        this.giorno = giorno;
    }

    public void addMeal(MealTakenBean meal) {
        if (meal == null) {
            throw new IllegalArgumentException("Il pasto non può essere null.");
        }
        pastiAssunti.add(meal);
    }

    public List<MealTakenBean> getMealsTaken() {
        return pastiAssunti;
    }

}
