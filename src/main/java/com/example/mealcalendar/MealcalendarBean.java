package com.example.mealcalendar;

import java.time.LocalDate;

public class MealcalendarBean {

    private LocalDate data;
    private String ora;
    private String user;
    private String scelta;

    public MealcalendarBean(LocalDate data, String ora, String user, String scelta) {
        this.data = data;
        this.ora = ora;
        this.user=user;
        this.scelta=scelta;
    }

    public LocalDate getData() {
        return data;
    }
    public String getOra() {
        return ora;
    }
    public String getUser() {
        return user;
    }
    public String getScelta() {
        return scelta;
    }

}
