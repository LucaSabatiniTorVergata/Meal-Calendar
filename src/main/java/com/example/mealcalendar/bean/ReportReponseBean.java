package com.example.mealcalendar.bean;

public class ReportReponseBean {
    private String dietName;
    private String nutritionistName;
    private String responseText;

    public ReportReponseBean(String dietName, String nutritionistName, String responseText) {
        setDietName(dietName);
        setNutritionistName(nutritionistName);
        setResponseText(responseText);
    }

    public void setDietName(String dietName) {
        if (dietName == null || dietName.isBlank()) {
            throw new IllegalArgumentException("Il nome della dieta non può essere vuoto o null.");
        }
        this.dietName = dietName;
    }

    public void setNutritionistName(String nutritionistName) {
        if (nutritionistName == null || nutritionistName.isBlank()) {
            throw new IllegalArgumentException("Il nome del nutrizionista non può essere vuoto o null.");
        }
        this.nutritionistName = nutritionistName;
    }

    public void setResponseText(String responseText) {
        if (responseText == null || responseText.isBlank()) {
            throw new IllegalArgumentException("Il testo della risposta non può essere vuoto o null.");
        }
        this.responseText = responseText;
    }

    public String getResponseText() {
        return responseText;
    }

    public String getDietName() {
        return dietName;
    }

    public String getNutritionistName() {
        return nutritionistName;
    }

}
