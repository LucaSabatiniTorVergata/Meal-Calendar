package com.example.mealcalendar;

import java.time.LocalDate;

public class MealcalendarBean {

    private LocalDate data;
    private String ora;
    private String user;

    public MealcalendarBean(LocalDate data, String ora, String user) {
        this.data = data;
        this.ora = ora;
        this.user=user;
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

}
