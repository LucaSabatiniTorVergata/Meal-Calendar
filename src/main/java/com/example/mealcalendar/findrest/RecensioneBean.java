package com.example.mealcalendar.findrest;

public class RecensioneBean {
    private String id;
    private String ristoranteId;
    private String username;
    private String testo;
    private int voto;

    public RecensioneBean() {}

    public String getId() { return id; }
    public void setId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID recensione non può essere vuoto");
        }
        this.id = id.trim();
    }

    public String getRistoranteId() { return ristoranteId; }
    public void setRistoranteId(String ristoranteId) {
        if (ristoranteId == null || ristoranteId.isBlank()) {
            throw new IllegalArgumentException("ID ristorante obbligatorio");
        }
        this.ristoranteId = ristoranteId.trim();
    }

    public String getUsername() { return username; }
    public void setUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username obbligatorio");
        }
        this.username = username.trim();
    }

    public String getTesto() { return testo; }
    public void setTesto(String testo) {
        if (testo == null || testo.isBlank()) {
            throw new IllegalArgumentException("Testo della recensione obbligatorio");
        }
        this.testo = testo.trim();
    }

    public int getVoto() { return voto; }
    public void setVoto(int voto) {
        if (voto < 1 || voto > 5) {
            throw new IllegalArgumentException("Voto deve essere tra 1 e 5");
        }
        this.voto = voto;
    }
}
