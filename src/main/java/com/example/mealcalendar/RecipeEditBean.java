package com.example.mealcalendar;

public class RecipeEditBean {

    private String user ;

    public RecipeEditBean(String username) {

        this.user = username;
    }

    public String getUser() {
        return user;
    }

}
