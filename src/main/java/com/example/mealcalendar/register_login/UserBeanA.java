package com.example.mealcalendar.register_login;

public class UserBeanA {

    private String username;
    private String email;
    private String password;
    private String ruolo;

    public UserBeanA(String username, String email, String password, String ruolo) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRuolo() { return ruolo; }

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }
}


