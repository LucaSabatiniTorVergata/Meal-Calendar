package com.example.mealcalendar;

import java.time.LocalDate;

public class SessionManagerSLT {

    private static SessionManagerSLT instance = null;
    private String loggedInUsername;
    private String loggedrole;
    private boolean fSDataBase;
    private boolean demo;
    private boolean ram=true;//solo per gui
    private boolean db=true;//solo per gui
    private boolean fs=true;//solo per gui
    private LocalDate datas;
    private String oras;
    private String ruolo;

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

    public void setLoggedRole(String role) {this.loggedrole = role;}

    public String getLoggedRole() {return loggedrole;}

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
        loggedrole = null;

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
    public void setDatas(LocalDate datas) {
        this.datas = datas;
    }
    public LocalDate getDatas() {
        return datas;
    }
    public void setOras(String oras) {
        this.oras = oras;
    }
    public String getOras() {
        return oras;
    }
    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }
    public String getRuolo() {
        return ruolo;
    }
}
