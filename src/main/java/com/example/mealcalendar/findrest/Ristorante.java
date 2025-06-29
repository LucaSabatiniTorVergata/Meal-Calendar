package com.example.mealcalendar.findrest;

public class Ristorante {
    private String id;
    private String nome;
    private String password;
    private int tavoliDisponibili;
    private int tavoliPrenotati;
    private boolean isAperto;
    private String citta;  // 🆕 Nuovo campo città

    public Ristorante(String id, String nome, String password,
                      int tavoliDisponibili, int tavoliPrenotati,
                      boolean isAperto, String citta) {
        this.id = id;
        this.nome = nome;
        this.password = password;
        this.tavoliDisponibili = tavoliDisponibili;
        this.tavoliPrenotati = tavoliPrenotati;
        this.isAperto = isAperto;
        this.citta = citta;  // 🆕 Assegna città
    }

    // Getters e Setters
    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getPassword() { return password; }
    public int getTavoliDisponibili() { return tavoliDisponibili; }
    public int getTavoliPrenotati() { return tavoliPrenotati; }
    public boolean isAperto() { return isAperto; }
    public String getCitta() { return citta; }  // 🆕 Getter città

    public void setTavoliDisponibili(int tavoliDisponibili) {
        this.tavoliDisponibili = tavoliDisponibili;
    }

    public void setTavoliPrenotati(int tavoliPrenotati) {
        this.tavoliPrenotati = tavoliPrenotati;
    }

    public void setAperto(boolean aperto) {
        isAperto = aperto;
    }

    public void setCitta(String citta) {  // 🆕 Setter città
        this.citta = citta;
    }

    @Override
    public String toString() {
        return "Ristorante{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", tavoliDisponibili=" + tavoliDisponibili +
                ", tavoliPrenotati=" + tavoliPrenotati +
                ", isAperto=" + isAperto +
                ", citta='" + citta + '\'' +
                '}';
    }
}
