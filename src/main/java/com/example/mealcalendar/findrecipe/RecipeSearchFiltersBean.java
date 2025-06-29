package com.example.mealcalendar.findrecipe;

import java.util.Set;

public class RecipeSearchFiltersBean {


    private static final Set<String> DIETE_VALIDE = Set.of("vegan", "vegetarian","omnivorous");

    private static final Set<String> PASTI_VALIDI = Set.of("breakfast", "lunch", "dinner");

    private String tipoDieta;
    private String tipoPasto;

    public RecipeSearchFiltersBean() {
        //uso i set e get
    }

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
        this.tipoDieta = tipoDieta;

    }
    public void setTipoPasto(String pasto)
    {
        if (pasto == null || !PASTI_VALIDI.contains(pasto.toLowerCase())) {
            throw new IllegalArgumentException("Pasto non valido! Scelte: " + PASTI_VALIDI);
        }
        this.tipoPasto = pasto;
    }


    //Ultimo push (speriamo)
}
