package com.example.mealcalendar;

public class RecipeFiltersBean {

    private String tipoDieta;
    private String tipoPasto;

    public RecipeFiltersBean(String tipoDieta,String tipoPasto) {
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
