package com.example.mealcalendar.model;

import java.time.LocalDate;

public class PrenotazioneEntity {

    private Integer id; // allineato a PrenotazioneBean
    private LocalDate dataPrenotazione;
    private LocalDate dataScadenza;
    private String oraPrenotazione;
    private String usernameUtente;    // allineato alla Bean
    private String nomeRistorante;    // allineato alla Bean
    private int postiASedere;
    private boolean scaduta;          // aggiunto per allineamento

    // Costruttore vuoto
    public PrenotazioneEntity() {}

    // Costruttore completo
    public PrenotazioneEntity(Integer id, LocalDate dataPrenotazione, LocalDate dataScadenza,
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

    // Getter e Setter
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

    // Metodo per dettagli (allineato)
    public String dettagliPrenotazione() {
        return "Prenotazione [ID=" + id +
                ", Data Prenotazione=" + dataPrenotazione +
                ", Ora=" + oraPrenotazione +
                ", Data Scadenza=" + dataScadenza +
                ", Utente=" + usernameUtente +
                ", Ristorante=" + nomeRistorante +
                ", Posti a sedere=" + postiASedere +
                ", Scaduta=" + scaduta +
                "]";
    }
}
