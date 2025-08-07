package com.example.mealcalendar.bean;

import java.util.ArrayList;
import java.util.List;

public class DayTakenBean {
    private int giorno;
    private List<MealTakenBean> pastiAssunti = new ArrayList<>();

    public int getGiorno() {
        return giorno;
    }

    public void setGiorno(int giorno) {
        this.giorno = giorno;
    }

    public List<MealTakenBean> getPastiAssunti() {
        return pastiAssunti;
    }

    public void addPastoAssunto(MealTakenBean meal) {
        pastiAssunti.add(meal);
    }
}
