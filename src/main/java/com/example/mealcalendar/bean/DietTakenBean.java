package com.example.mealcalendar.bean;

import java.util.ArrayList;
import java.util.List;

public class DietTakenBean {

    private String user;  // utile per identificare chi ha inserito i pasti
    private List<DayTakenBean> giorniAssunti = new ArrayList<>();



    public void setUser(String user) {
        if (user == null || user.isBlank()) {
            throw new IllegalArgumentException("L'utente non può essere null o vuoto.");
        }
        this.user = user;
    }

    public void addDay(DayTakenBean dietTakenBean) {
        if (dietTakenBean == null) {
            throw new IllegalArgumentException("Il giorno da aggiungere non può essere null.");
        }
        giorniAssunti.add(dietTakenBean);
    }

    public List<DayTakenBean> getDietTaken() {
        return giorniAssunti;
    }
    public String getUser() {
        return user;
    }
}
