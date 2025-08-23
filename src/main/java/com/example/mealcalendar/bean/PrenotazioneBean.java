package com.example.mealcalendar.bean;

import java.time.LocalDate;

public class PrenotazioneBean {

    private Integer id;
    private LocalDate dataPrenotazione;
    private LocalDate dataScadenza;
    private String oraPrenotazione;
    private String usernameUtente;
    private String nomeRistorante;
    private int postiASedere;
    private boolean scaduta; // flag per prenotazioni scadute

    public PrenotazioneBean(Integer id, LocalDate dataPrenotazione, LocalDate dataScadenza,
                            String oraPrenotazione, String usernameUtente, String nomeRistorante,
                            int postiASedere) {
        this.id = id;
        this.dataPrenotazione = dataPrenotazione;
        this.dataScadenza = dataScadenza;
        this.oraPrenotazione = oraPrenotazione;
        this.usernameUtente = usernameUtente;
        this.nomeRistorante = nomeRistorante;
        this.postiASedere = postiASedere;
        this.scaduta = false;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getDataPrenotazione() { return dataPrenotazione; }
    public void setDataPrenotazione(LocalDate dataPrenotazione) { this.dataPrenotazione = dataPrenotazione; }

    public LocalDate getDataScadenza() { return dataScadenza; }
    public void setDataScadenza(LocalDate dataScadenza) { this.dataScadenza = dataScadenza; }

    public String getOraPrenotazione() { return oraPrenotazione; }
    public void setOraPrenotazione(String oraPrenotazione) { this.oraPrenotazione = oraPrenotazione; }

    public String getUsernameUtente() { return usernameUtente; }
    public void setUsernameUtente(String usernameUtente) { this.usernameUtente = usernameUtente; }

    public String getNomeRistorante() { return nomeRistorante; }
    public void setNomeRistorante(String nomeRistorante) { this.nomeRistorante = nomeRistorante; }

    public int getPostiASedere() { return postiASedere; }
    public void setPostiASedere(int postiASedere) { this.postiASedere = postiASedere; }

    public boolean isScaduta() { return scaduta; }
    public void setScaduta(boolean scaduta) { this.scaduta = scaduta; }

    @Override
    public String toString() {
        return id + ";" + dataPrenotazione + ";" + dataScadenza + ";" + oraPrenotazione + ";" +
                usernameUtente + ";" + nomeRistorante + ";" + postiASedere;
    }
}
