package com.example.mealcalendar.makediet;

import java.util.List;

public class GiornoDietaEntity {

    private int numeroGiorno; // es: 1 = Lunedì
    private PastoEntity colazione;
    private PastoEntity pranzo;
    private PastoEntity cena;

    public GiornoDietaEntity(int numeroGiorno, PastoEntity colazione, PastoEntity pranzo, PastoEntity cena) {
        this.numeroGiorno = numeroGiorno;
        this.colazione = colazione;
        this.pranzo = pranzo;
        this.cena = cena;
    }

    public int getNumeroGiorno() {
        return numeroGiorno;
    }

    public void setNumeroGiorno(int numeroGiorno) {
        this.numeroGiorno = numeroGiorno;
    }

    public PastoEntity getColazione() {
        return colazione;
    }

    public void setColazione(PastoEntity colazione) {
        this.colazione = colazione;
    }

    public PastoEntity getPranzo() {
        return pranzo;
    }

    public void setPranzo(PastoEntity pranzo) {
        this.pranzo = pranzo;
    }

    public PastoEntity getCena() {
        return cena;
    }

    public void setCena(PastoEntity cena) {
        this.cena = cena;
    }
}

