package com.example.mealcalendar.makediet;

import java.util.List;

public class GiornoBean {

    private int ngiorno;
    private PastoBean colazione;
    private PastoBean pranzo;
    private PastoBean cena;


    public void setColazione(PastoBean colazione) {
        this.colazione = colazione;
    }
    public PastoBean getColazione() {return colazione;}

    public void setPranzo(PastoBean pranzo) {this.pranzo = pranzo;}

    public PastoBean getPranzo() {return pranzo;}

    public void setCena(PastoBean cena) {this.cena = cena;}

    public PastoBean getCena() {return cena;}

    public void setNgiorno(int ngiorno) {
        this.ngiorno = ngiorno;
    }
    public int getNgiorno() {return ngiorno;}

}
