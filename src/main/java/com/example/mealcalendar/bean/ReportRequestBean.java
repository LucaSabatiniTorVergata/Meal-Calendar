package com.example.mealcalendar.bean;

import java.time.LocalDateTime;

public class ReportRequestBean {
    private String userEmail;
    private String nutritionistEmail;
    private String dietName;
    private String dietDescription;
    private LocalDateTime requestDate;
    private boolean answered;
    private String response;
    private DietTakenBean dieTaken;

    // getters e setters

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getNutritionistEmail() {
        return nutritionistEmail;
    }

    public void setNutritionistEmail(String nutritionistEmail) {
        this.nutritionistEmail = nutritionistEmail;
    }

    public String getDietName() {
        return dietName;
    }

    public void setDietName(String dietName) {
        this.dietName = dietName;
    }

    public String getDietDescription() {
        return dietDescription;
    }

    public void setDietDescription(String dietDescription) {
        this.dietDescription = dietDescription;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public DietTakenBean getDietTaken() {
        return dieTaken;
    }

    public void setDietTaken(DietTakenBean dietTaken) {
        this.dieTaken=dietTaken;
    }

    @Override
    public String toString() {
        return dietName + " - da: " + userEmail;
    }
}
