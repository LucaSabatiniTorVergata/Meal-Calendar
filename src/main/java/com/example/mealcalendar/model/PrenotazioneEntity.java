package com.example.mealcalendar.model;

import java.time.LocalDate;

public class PrenotazioneEntity {

    private String idPrenotazione;
    private LocalDate dataPrenotazione;
    private LocalDate dataScadenza;
    private String oraPrenotazione; // nuovo attributo
    private UserEntity utente;
    private RistoranteEntity ristorante;
    private int postiASedere;

    // Costruttore vuoto
    public PrenotazioneEntity() {}

    // Costruttore completo
    public PrenotazioneEntity(String idPrenotazione, LocalDate dataPrenotazione, LocalDate dataScadenza,
                              String oraPrenotazione, UserEntity utente, RistoranteEntity ristorante, int postiASedere) {
        this.idPrenotazione = idPrenotazione;
        this.dataPrenotazione = dataPrenotazione;
        this.dataScadenza = dataScadenza;
        this.oraPrenotazione = oraPrenotazione;
        this.utente = utente;
        this.ristorante = ristorante;
        this.postiASedere = postiASedere;
    }

    // Getter e Setter
    public String getIdPrenotazione() { return idPrenotazione; }
    public void setIdPrenotazione(String idPrenotazione) { this.idPrenotazione = idPrenotazione; }

    public LocalDate getDataPrenotazione() { return dataPrenotazione; }
    public void setDataPrenotazione(LocalDate dataPrenotazione) { this.dataPrenotazione = dataPrenotazione; }

    public LocalDate getDataScadenza() { return dataScadenza; }
    public void setDataScadenza(LocalDate dataScadenza) { this.dataScadenza = dataScadenza; }

    public String getOraPrenotazione() { return oraPrenotazione; }
    public void setOraPrenotazione(String oraPrenotazione) { this.oraPrenotazione = oraPrenotazione; }

    public UserEntity getUtente() { return utente; }
    public void setUtente(UserEntity utente) { this.utente = utente; }

    public RistoranteEntity getRistorante() { return ristorante; }
    public void setRistorante(RistoranteEntity ristorante) { this.ristorante = ristorante; }

    public int getPostiASedere() { return postiASedere; }
    public void setPostiASedere(int postiASedere) { this.postiASedere = postiASedere; }

    // Metodo per dettagli
    public String dettagliPrenotazione() {
        return "Prenotazione [ID=" + idPrenotazione +
                ", Data Prenotazione=" + dataPrenotazione +
                ", Ora=" + oraPrenotazione +
                ", Data Scadenza=" + dataScadenza +
                ", Utente=" + (utente != null ? utente.getNome() : "N/A") +
                ", Ristorante=" + (ristorante != null ? ristorante.getNome() : "N/A") +
                ", Posti a sedere=" + postiASedere +
                "]";
    }
}
