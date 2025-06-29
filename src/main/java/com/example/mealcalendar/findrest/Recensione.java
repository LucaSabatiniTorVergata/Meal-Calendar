package com.example.mealcalendar.findrest;

public class Recensione {
    private String id;
    private String ristoranteId;
    private String username;
    private String testo;
    private int voto;

    public Recensione(String id, String ristoranteId, String username, String testo, int voto) {
        this.id = id;
        this.ristoranteId = ristoranteId;
        this.username = username;
        this.testo = testo;
        this.voto = voto;
    }

    // Getters
    public String getId() { return id; }
    public String getRistoranteId() { return ristoranteId; }
    public String getUsername() { return username; }
    public String getTesto() { return testo; }
    public int getVoto() { return voto; }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public void setVoto(int voto) {
        if (voto >= 1 && voto <= 5) {
            this.voto = voto;
        }
    }

    @Override
    public String toString() {
        return "Recensione{" +
                "id='" + id + '\'' +
                ", ristoranteId='" + ristoranteId + '\'' +
                ", username='" + username + '\'' +
                ", voto=" + voto +
                ", testo='" + testo + '\'' +
                '}';
    }
}
