package com.example.mealcalendar;

public class RecipeSearchFiltersBean {

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

    public void setTipoDieta(String tipoDieta) {
        this.tipoDieta = tipoDieta;
    }
    public void setTipoPasto(String tipoPasto) {
        this.tipoPasto = tipoPasto;
    }

}
