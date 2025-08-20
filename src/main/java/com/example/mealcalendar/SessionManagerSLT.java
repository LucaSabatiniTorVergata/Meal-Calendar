package com.example.mealcalendar;

import com.example.mealcalendar.model.TipologiaRistorante;

import java.time.LocalDate;

public class SessionManagerSLT {

    private static SessionManagerSLT instance = null;

    private PersistenceType persistenceType;
    private String loggedInUsername;
    private String loggedrole;
    private String loggedmail;
    private String loggedpassword;
    private Boolean requestnutr;

    private LocalDate datas;
    private String oras;
    private String ruolo;

    // nuovo campo per ristoranti
    private TipologiaRistorante tipologiaRistorante;

    private SessionManagerSLT() {}

    public static SessionManagerSLT getInstance() {
        if (instance == null) {
            instance = new SessionManagerSLT();
        }
        return instance;
    }

    public void setLoggedpassword(String loggedpassword) { this.loggedpassword = loggedpassword; }
    public void setLoggedInUsername(String username) { this.loggedInUsername = username; }
    public String getLoggedInUsername() { return loggedInUsername; }

    public void setLoggedRole(String role) { this.loggedrole = role; }
    public String getLoggedRole() { return loggedrole; }

    public String getLoggedpassword(){ return loggedpassword; }

    public void logout() {
        loggedInUsername = null;
        loggedrole = null;
        loggedmail = null;
        loggedpassword = null;
        tipologiaRistorante = null;
    }

    public void setLoggedmail(String loggedmail) { this.loggedmail = loggedmail; }
    public String getLoggedmail() { return loggedmail; }

    public void setDatas(LocalDate datas) { this.datas = datas; }
    public LocalDate getDatas() { return datas; }

    public void setOras(String oras) { this.oras = oras; }
    public String getOras() { return oras; }

    public void setRuolo(String ruolo) { this.ruolo = ruolo; }
    public String getRuolo() { return ruolo; }

    public void setPersistenceType(PersistenceType type) { this.persistenceType = type; }
    public PersistenceType getPersistenceType() { return persistenceType; }

    public void setRequestnutr(Boolean requestnutr) { this.requestnutr = requestnutr; }
    public Boolean getRequestnutr() { return requestnutr; }

    // getter e setter per tipologia ristorante
    public TipologiaRistorante getTipologiaRistorante() { return tipologiaRistorante; }
    public void setTipologiaRistorante(TipologiaRistorante tipologiaRistorante) { this.tipologiaRistorante = tipologiaRistorante; }
}
