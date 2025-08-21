package com.example.mealcalendar.bean;

import java.time.LocalDate;

public class PrenotazioneBean {

    private String idPrenotazione;
    private LocalDate dataPrenotazione;
    private LocalDate dataScadenza;
    private String usernameUtente;     // dal UserEntity
    private String nomeRistorante;     // dal RistoranteEntity
    private int postiASedere;

    // Costruttore vuoto
    public PrenotazioneBean() {}

    // Costruttore completo
    public PrenotazioneBean(String idPrenotazione, LocalDate dataPrenotazione, LocalDate dataScadenza,
                            String usernameUtente, String nomeRistorante, int postiASedere) {
        this.idPrenotazione = idPrenotazione;
        this.dataPrenotazione = dataPrenotazione;
        this.dataScadenza = dataScadenza;
        this.usernameUtente = usernameUtente;
        this.nomeRistorante = nomeRistorante;
        this.postiASedere = postiASedere;
    }

    // Getter e Setter
    public String getIdPrenotazione() {
        return idPrenotazione;
    }

    public void setIdPrenotazione(String idPrenotazione) {
        this.idPrenotazione = idPrenotazione;
    }

    public LocalDate getDataPrenotazione() {
        return dataPrenotazione;
    }

    public void setDataPrenotazione(LocalDate dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public String getUsernameUtente() {
        return usernameUtente;
    }

    public void setUsernameUtente(String usernameUtente) {
        this.usernameUtente = usernameUtente;
    }

    public String getNomeRistorante() {
        return nomeRistorante;
    }

    public void setNomeRistorante(String nomeRistorante) {
        this.nomeRistorante = nomeRistorante;
    }

    public int getPostiASedere() {
        return postiASedere;
    }

    public void setPostiASedere(int postiASedere) {
        this.postiASedere = postiASedere;
    }

    @Override
    public String toString() {
        return "PrenotazioneBean{" +
                "idPrenotazione='" + idPrenotazione + '\'' +
                ", dataPrenotazione=" + dataPrenotazione +
                ", dataScadenza=" + dataScadenza +
                ", usernameUtente='" + usernameUtente + '\'' +
                ", nomeRistorante='" + nomeRistorante + '\'' +
                ", postiASedere=" + postiASedere +
                '}';
    }
}
