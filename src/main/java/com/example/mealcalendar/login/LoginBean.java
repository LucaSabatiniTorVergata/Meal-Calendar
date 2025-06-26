package com.example.mealcalendar.login;

public class LoginBean {

    private String username;
    private String password;
    private String ruolo;

    public LoginBean(String username, String password,String ruolo) {
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRuolo() { return ruolo; }
}
