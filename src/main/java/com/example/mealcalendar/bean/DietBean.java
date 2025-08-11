package com.example.mealcalendar.bean;

import java.util.ArrayList;
import java.util.List;


public class DietBean {

        private String nome;
        private String descrizione;
        private int durata;
        private List<DayBean> giorni = new ArrayList<>();
        private String nutritionistUsername;

        public void setNome(String n) {
            if (n == null) {
                throw new IllegalArgumentException("Il nome della dieta non può essere vuoto.");
            }
            this.nome = n;
        }

        public void setDescrizione(String d) {

            if (d ==null) {
                throw new IllegalArgumentException("La descrizione non può essere una stringa vuota.");
            }
            this.descrizione = d;
        }

        public void setDurata(int d) {
            if (d <= 0) {
                throw new IllegalArgumentException("La durata deve essere un intero positivo.");
            }
            this.durata = d;
        }

        public void addDay(DayBean g) {

            if (g == null) {
                throw new IllegalArgumentException("Il giorno non può essere nullo.");
            }
            this.giorni.add(g);

        }
        public void setGiorni(List<DayBean> giorni) {
        if (giorni == null) {
            throw new IllegalArgumentException("Il pasto non può essere null.");
        }
        this.giorni = giorni;

        }

        public String getNome() {
            return nome;
        }

        public String getDescrizione() {
            return descrizione;
        }

        public int getDurata() {
            return durata;
        }

        public List<DayBean> getGiorni() {
            return giorni;
        }


        public String getNutritionistUsername() {
                return nutritionistUsername;
        }

        public void setNutritionistUsername(String nutritionistUsername) {
            if(nutritionistUsername == null) {
                throw new IllegalArgumentException("Il nutritionist non può essere null.");
            }
            this.nutritionistUsername = nutritionistUsername;
        }

}

