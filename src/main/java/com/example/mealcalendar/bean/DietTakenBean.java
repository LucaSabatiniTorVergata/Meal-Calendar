package com.example.mealcalendar.bean;

import java.util.ArrayList;
import java.util.List;

public class DietTakenBean {

    private String user;  // utile per identificare chi ha inserito i pasti
    private List<DayTakenBean> giorniAssunti=new ArrayList<>();

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setGiorniAssunti(List<DayTakenBean> giorniAssunti) {
        this.giorniAssunti = giorniAssunti;
    }

    public List<DayTakenBean> getDietTaken() {
        return giorniAssunti;
    }

    public void addDay(DayTakenBean dietTakenBean) {
        giorniAssunti.add(dietTakenBean);
    }
}
