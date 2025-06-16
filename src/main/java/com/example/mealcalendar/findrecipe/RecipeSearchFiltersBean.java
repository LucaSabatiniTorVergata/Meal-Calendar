package com.example.mealcalendar.findrecipe;

import java.util.Set;

public class RecipeSearchFiltersBean {


    private static final Set<String> DIETE_VALIDE = Set.of("vegano", "vegetariano","onnivoro");

    private static final Set<String> PASTI_VALIDI = Set.of("colazione", "pranzo", "cena");

    private String tipoDieta;
    private String tipoPasto;

    public RecipeSearchFiltersBean(String tipoDieta, String tipoPasto) {
           this.tipoDieta = tipoDieta;
           this.tipoPasto = tipoPasto;
    }
    public RecipeSearchFiltersBean() {}

    public String getTipoDieta() {
        return tipoDieta;
    }

    public String getTipoPasto() {
        return tipoPasto;
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
    public void setTipoPasto(String pasto)
    {
        if (pasto == null || !PASTI_VALIDI.contains(pasto.toLowerCase())) {
            throw new IllegalArgumentException("Pasto non valido! Scelte: " + PASTI_VALIDI);
        }
        this.tipoPasto = tipoPasto;
    }


    //Ultimo push (speriamo)
}
