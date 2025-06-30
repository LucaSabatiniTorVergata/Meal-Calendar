package com.example.mealcalendar.login;

import com.example.mealcalendar.makediet.DietaEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserEntity {


    @JsonProperty
    private String username;
    @JsonProperty
    private String email;
    @JsonProperty
    private String password;
    @JsonProperty
    private String role;
    @JsonProperty
    private DietaEntity dietaAssegnata;

    public UserEntity() {}

    public UserEntity(String username, String email, String password, String role,DietaEntity dietaAssegnata) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.dietaAssegnata = dietaAssegnata;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public DietaEntity getDietaAssegnata() { return dietaAssegnata; }
    public void setDietaAssegnata(DietaEntity dietaAssegnata) {}
}

