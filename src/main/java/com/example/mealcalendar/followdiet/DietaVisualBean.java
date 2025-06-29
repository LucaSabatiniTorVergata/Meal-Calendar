package com.example.mealcalendar.followdiet;
import javafx.beans.property.*;

public class DietaVisualBean {

    private final StringProperty nome = new SimpleStringProperty();
    private final StringProperty autore = new SimpleStringProperty();
    private final StringProperty descrizione = new SimpleStringProperty();
    private final IntegerProperty durata = new SimpleIntegerProperty();

    public DietaVisualBean() {}

    public DietaVisualBean(String nome, String autore, String descrizione, int durata) {
        this.nome.set(nome);
        this.autore.set(autore);
        this.descrizione.set(descrizione);
        this.durata.set(durata);
    }

    // Getters e setters
    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public String getAutore() {
        return autore.get();
    }

    public void setAutore(String autore) {
        this.autore.set(autore);
    }

    public StringProperty autoreProperty() {
        return autore;
    }

    public String getDescrizione() {
        return descrizione.get();
    }

    public void setDescrizione(String descrizione) {
        this.descrizione.set(descrizione);
    }

    public StringProperty descrizioneProperty() {
        return descrizione;
    }

    public int getDurata() {
        return durata.get();
    }

    public void setDurata(int durata) {
        this.durata.set(durata);
    }

    public IntegerProperty durataProperty() {
        return durata;
    }

}

