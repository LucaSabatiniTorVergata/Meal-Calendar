package com.example.mealcalendar.findrecipe;

public class RecipeEdit2Bean {

    private String ricettapresa;
    private String ricetta;
    private String tdieta;
    private String tpasto;
    private String numingred;
    private String ingred;
    private String desrcip;
    private String autor;

    // Costruttore privato per impedire l'uso diretto
    private RecipeEdit2Bean(Builder builder) {
        this.ricettapresa = builder.ricettapresa;
        this.ricetta = builder.ricetta;
        this.tdieta = builder.tdieta;
        this.tpasto = builder.tpasto;
        this.numingred = builder.numingred;
        this.ingred = builder.ingred;
        this.desrcip = builder.desrcip;
        this.autor = builder.autor;
    }

    // Getter
    public String getRicettapresa() {
        return ricettapresa;
    }

    public String getricetta() {
        return ricetta;
    }

    public String getTdieta() {
        return tdieta;
    }

    public String getTpasto() {
        return tpasto;
    }

    public String getNumingred() {
        return numingred;
    }

    public String getIngred() {
        return ingred;
    }

    public String getDesrcip() {
        return desrcip;
    }

    public String getAutor() {
        return autor;
    }

    // Builder static inner class
    public static class Builder {

        private String ricettapresa;
        private String ricetta;
        private String tdieta;
        private String tpasto;
        private String numingred;
        private String ingred;
        private String desrcip;
        private String autor;

        // Setter per ogni campo, restituisce 'this' per la chaining
        public Builder setRicettapresa(String ricettapresa) {
            this.ricettapresa = ricettapresa;
            return this;
        }

        public Builder setRicetta(String ricetta) {
            this.ricetta = ricetta;
            return this;
        }

        public Builder setTdieta(String tdieta) {
            this.tdieta = tdieta;
            return this;
        }

        public Builder setTpasto(String tpasto) {
            this.tpasto = tpasto;
            return this;
        }

        public Builder setNumingred(String numingred) {
            this.numingred = numingred;
            return this;
        }

        public Builder setIngred(String ingred) {
            this.ingred = ingred;
            return this;
        }

        public Builder setDesrcip(String desrcip) {
            this.desrcip = desrcip;
            return this;
        }

        public Builder setAutor(String autor) {
            this.autor = autor;
            return this;
        }

        // Metodo per costruire l'oggetto finale
        public RecipeEdit2Bean build() {
            return new RecipeEdit2Bean(this);
        }
    }
}
