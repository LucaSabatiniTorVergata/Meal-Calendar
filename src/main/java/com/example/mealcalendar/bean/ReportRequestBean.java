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



    public void setUserEmail(String userEmail) {
        if (userEmail == null || userEmail.isBlank()) {
            throw new IllegalArgumentException("L'email dell'utente non può essere nulla o vuota.");
        }
        this.userEmail = userEmail;
    }

    public void setNutritionistEmail(String nutritionistEmail) {
        if (nutritionistEmail == null || nutritionistEmail.isBlank()) {
            throw new IllegalArgumentException("L'email del nutrizionista non può essere nulla o vuota.");
        }
        this.nutritionistEmail = nutritionistEmail;
    }

    public void setDietTaken(DietTakenBean dietTaken) {
        if (dietTaken == null) {
            throw new IllegalArgumentException("Il DietTakenBean non può essere nullo.");
        }
        this.dieTaken = dietTaken;
    }

    public void setDietName(String dietName) {
        if (dietName == null || dietName.isBlank()) {
            throw new IllegalArgumentException("Il nome della dieta non può essere nullo o vuoto.");
        }
        this.dietName = dietName;
    }

    public void setDietDescription(String dietDescription) {
        if (dietDescription == null || dietDescription.isBlank()) {
            throw new IllegalArgumentException("La descrizione della dieta non può essere nulla o vuota.");
        }
        this.dietDescription = dietDescription;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        if (requestDate == null) {
            throw new IllegalArgumentException("La data della richiesta non può essere nulla.");
        }
        this.requestDate = requestDate;
    }

    public void setResponse(String response) {
        if (response == null || response.isBlank()) {
            throw new IllegalArgumentException("La risposta non può essere nulla o vuota.");
        }
        this.response = response;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public String getResponse() {
        return response;
    }

    public DietTakenBean getDietTaken() {
        return dieTaken;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getDietName() {
        return dietName;
    }

    public String getNutritionistEmail() {
        return nutritionistEmail;
    }

    public boolean isAnswered() {
        return answered;
    }

    public String getDietDescription() {
        return dietDescription;
    }


    @Override
    public String toString() {
        return dietName + " - da: " + userEmail;
    }
}
