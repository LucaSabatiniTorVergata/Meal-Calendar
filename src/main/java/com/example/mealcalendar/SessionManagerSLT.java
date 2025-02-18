package com.example.mealcalendar;

public class SessionManagerSLT {

    private static SessionManagerSLT instance = null;
    private String loggedInUsername;
    private boolean fSDataBase;
    private boolean demo;
    private boolean ram=true;
    private boolean db=true;
    private boolean fs=true;

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
        this.fSDataBase=fSDataBase;
    }

    public boolean getFSDataBase() {
        return fSDataBase;
    }

    public void setDemo(boolean altroDato) {
        this.demo = altroDato;
    }

    public boolean getDemo() {
        return demo;
    }

    public void logout() {
        loggedInUsername = null;
        fSDataBase = false;
        demo = false;

    }

    public void setRam(boolean ram) {
        this.ram = ram;
    }
    public boolean getRam() {
        return ram;
    }
    public void setDB(boolean db) {
        this.db = db;
    }
    public boolean getDB() {
        return db;
    }
    public void setFs(boolean fs) {
        this.fs = fs;
    }
    public boolean getFs() {
        return fs;
    }
}
