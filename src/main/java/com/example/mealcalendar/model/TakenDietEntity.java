package com.example.mealcalendar.model;

import java.util.ArrayList;
import java.util.List;

public class TakenDietEntity {

    private List<TakenDayEntity> days =new ArrayList<>();
    private String user;

    public TakenDietEntity(String userpassed) {this.user = userpassed;
    }

    public List<TakenDayEntity> getDays() {
        return days;
    }

    public void addDay(TakenDayEntity days) {
        this.days.add(days);
    }

    public String getUser() {
        return user;
    }

}


