package com.example.mealcalendar;

import com.example.mealcalendar.dao.CacheDietDAO;
import com.example.mealcalendar.dao.DietDAO;
import com.example.mealcalendar.dao.FileSystemDietDAO;
import com.example.mealcalendar.dao.RamDietDAO;

import java.time.LocalDate;

public class SessionManagerSLT {

    private static SessionManagerSLT instance = null;

    private PersistenceType persistenceType;


    private String loggedInUsername;
    private String loggedrole;
    private String loggedmail;
    private String loggedpassword;

    private boolean fSDataBase;//se vero allora stiamo in fs
    private boolean demo;//se vero allora stiamo in ram

    private LocalDate datas;
    private String oras;

    private String ruolo;


    private SessionManagerSLT() {

    }

    public static SessionManagerSLT getInstance() {
        if (instance == null) {
            instance = new SessionManagerSLT();
        }
        return instance;
    }

    public void setLoggedpassword(String loggedpassword) {
        this.loggedpassword = loggedpassword;
    }

    public void setLoggedInUsername(String username) {
        this.loggedInUsername = username;
    }

    public String getLoggedInUsername() {
        return loggedInUsername;
    }

    public void setLoggedRole(String role) {this.loggedrole = role;}

    public String getLoggedpassword(){
        return loggedpassword;
    }

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
        loggedmail = null;
        loggedpassword = null;

    }

    public void setLoggedmail(String loggedmail) {this.loggedmail = loggedmail;}
    public String getLoggedmail() {return loggedmail;}

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

    public void setPersistenceType(PersistenceType type) {

        this.persistenceType = type;
    }

    public DietDAO getDietDAO() {

        if(demo){
            return RamDietDAO.getInstance();
        }
        else{
            if(fSDataBase) {
                return new CacheDietDAO(new FileSystemDietDAO());
            }
            else{
                //database
            }
        }

       return null;
    }
}
