package com.example.mealcalendar.findrest;

import java.util.Set;

public class FiltersRestaurantBean {

    private String tipoDieta;
    private String pasto;
    private double distanza;

    private static final Set<String> DIETE_VALIDE = Set.of("vegano", "vegetariano","onnivoro");

    private static final Set<String> PASTI_VALIDI = Set.of("colazione", "pranzo", "cena");

    public String getTipoDieta() {
        return tipoDieta;
    }

    public String getPasto() {
        return pasto;
    }

    public double getDistanza() {
        return distanza;
    }

    public void setTipoDieta(String tipoDieta)
    {
        if (tipoDieta == null || !DIETE_VALIDE.contains(tipoDieta.toLowerCase())) {
            throw new IllegalArgumentException("Dieta non valida! Scelte: " + DIETE_VALIDE);
        }
        else{
            if (tipoDieta.equalsIgnoreCase("Onnivoro") ){
              this.tipoDieta = "";
            }
            else{
                this.tipoDieta = tipoDieta;
            }

        }

    }
    public void setPasto(String pasto)
    {
        if (pasto == null || !PASTI_VALIDI.contains(pasto.toLowerCase())) {
            throw new IllegalArgumentException("Pasto non valido! Scelte: " + PASTI_VALIDI);
        }
        this.pasto = pasto;
    }
    public void setDistanza(double distanza) {

        if (distanza < 0) {
            throw new IllegalArgumentException("La distanza non può essere negativa!");
        }
        this.distanza = distanza;
    }

}