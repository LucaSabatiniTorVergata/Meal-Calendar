package com.example.mealcalendar.bean;

public class ReportReponseBean {
    private String dietName;
    private String nutritionistName;
    private String responseText;

    public ReportReponseBean(String dietName, String nutritionistName, String responseText) {
        this.dietName = dietName;
        this.nutritionistName = nutritionistName;
        this.responseText = responseText;
    }

    public String getDietName() {
        return dietName;
    }

    public String getNutritionistName() {
        return nutritionistName;
    }

    public String getResponseText() {
        return responseText;
    }
}
