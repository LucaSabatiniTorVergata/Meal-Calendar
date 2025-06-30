package com.example.mealcalendar.makediet;

import com.fasterxml.jackson.annotation.JsonProperty;


public class GiornoDietaEntity {

    @JsonProperty
    private int numeroGiorno;// es: 1 = Lunedì
    @JsonProperty
    private PastoEntity colazione;
    @JsonProperty
    private PastoEntity pranzo;
    @JsonProperty
    private PastoEntity cena;


    public GiornoDietaEntity() {}

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

