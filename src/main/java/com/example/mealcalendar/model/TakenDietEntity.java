package com.example.mealcalendar.model;

import java.util.List;

public class TakenDietEntity {

    private List<TakenDayEntity> days;
    private String user;

    public TakenDietEntity(List<TakenDayEntity> days) {
        this.days = days;
    }

    public List<TakenDayEntity> getDays() {
        return days;
    }

    public void setDays(List<TakenDayEntity> days) {}


}


