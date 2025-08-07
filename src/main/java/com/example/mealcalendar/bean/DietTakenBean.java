package com.example.mealcalendar.bean;

import java.util.ArrayList;
import java.util.List;

public class DietTakenBean {

    private String user;  // utile per identificare chi ha inserito i pasti
    private List<DayTakenBean> giorniAssunti = new ArrayList<>();

    public String getUserEmail() {
        return user;
    }

    public void setUserEmail(String userEmail) {
        this.user = userEmail;
    }

    public List<DayTakenBean> getGiorniAssunti() {
        return giorniAssunti;
    }

    public void setGiorniAssunti(List<DayTakenBean> giorniAssunti) {
        this.giorniAssunti = giorniAssunti;
    }

    public void addGiornoAssunto(DayTakenBean giorno) {
        this.giorniAssunti.add(giorno);
    }
}
