package com.example.mealcalendar.model;

import java.time.LocalDateTime;

public class ReportRequestEntity {
    private final String userEmail;
    private final String nutritionist;
    private final DietEntity assignedDiet;
    private final TakenDietEntity takenDiet;
    private final LocalDateTime requestDate;
    private String responseText;
    private boolean answered;

    public ReportRequestEntity(String userEmail, String nutritionist,
                               DietEntity assignedDiet, TakenDietEntity takenDiet,
                               LocalDateTime requestDate) {
        this.userEmail = userEmail;
        this.nutritionist = nutritionist;
        this.assignedDiet = assignedDiet;
        this.takenDiet = takenDiet;
        this.requestDate = requestDate;
        this.answered = false;
    }


    public boolean isAnswered() { return answered; }
    public void setAnswered(boolean answered) { this.answered = answered; }
    public void setResponse(String responseText) { this.responseText = responseText; }
    public String getResponse() { return responseText; }

    public String getUserEmail() { return userEmail; }
    public String getNutritionistEmail() { return nutritionist; }
    public DietEntity getDietAssigned() { return assignedDiet; }
    public TakenDietEntity getDietTaken() { return takenDiet; }
    public LocalDateTime getRequestTime() { return requestDate; }
}


