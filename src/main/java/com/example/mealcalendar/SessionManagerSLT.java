package com.example.mealcalendar;

public class SessionManagerSLT {

    private static SessionManagerSLT instance = null;
    private String loggedInUsername;
    private String loggedEmail;
    private String preferenzaUtente;  // Nuovo campo per dati scelti all'inizio
    private String altroDato;          // Aggiungi tutto quello che serve

    private SessionManagerSLT() {}

    public static SessionManagerSLT getInstance() {
        if (instance == null) {
            instance = new SessionManagerSLT();
        }
        return instance;
    }

    public void setLoggedInUsername(String username) {
        this.loggedInUsername = username;
    }

    public String getLoggedInUsername() {
        return loggedInUsername;
    }

    public void setPreferenzaUtente(String preferenza) {
        this.preferenzaUtente = preferenza;
    }

    public String getPreferenzaUtente() {
        return preferenzaUtente;
    }

    public void setAltroDato(String dato) {
        this.altroDato = dato;
    }

    public String getAltroDato() {
        return altroDato;
    }

    public void logout() {
        loggedInUsername = null;
        preferenzaUtente = null;
        altroDato = null;
    }
}
