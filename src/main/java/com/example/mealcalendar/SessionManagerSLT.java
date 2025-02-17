package com.example.mealcalendar;

public class SessionManagerSLT {

    private static SessionManagerSLT instance = null;
    private String loggedInUsername;
    private boolean FSDataBase;
    private boolean demo;         // Aggiungi tutto quello che serve

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

    public void setFSDataBase(boolean fSDataBase) {
        this.FSDataBase=fSDataBase;
    }

    public boolean getFSDataBase() {
        return FSDataBase;
    }

    public void setDemo(boolean altroDato) {
        this.demo = altroDato;
    }

    public boolean getDemo() {
        return demo;
    }

    public void logout() {
        loggedInUsername = null;
        FSDataBase = false;
        demo = false;

    }
}
