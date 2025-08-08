package com.example.mealcalendar.register_login;

public class UserLoginBean {

    private String username;
    private String password;
    private String ruolo;

    public UserLoginBean(String username, String password, String ruolo) {
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRuolo() {
        return ruolo;
    }
}

