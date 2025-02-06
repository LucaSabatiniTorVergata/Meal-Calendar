package com.example.mealcalendar;

public class RecipeSearchFiltersBean {

    private String tipoDieta;
    private String tipoPasto;

    public RecipeSearchFiltersBean(String tipoDieta, String tipoPasto) {
           this.tipoDieta = tipoDieta;
           this.tipoPasto = tipoPasto;
    }

    public String getTipoDieta() {
        return tipoDieta;
    }

    public String getTipoPasto() {
        return tipoPasto;
    }

}
