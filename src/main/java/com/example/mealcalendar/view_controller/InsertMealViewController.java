package com.example.mealcalendar.view_controller;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.bean.*;
import com.example.mealcalendar.controller_applicativo.FollowDietController;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class InsertMealViewController {

    @FXML
    private Label dietStatusLabel;
    @FXML
    private VBox dietInputContainer;
    @FXML
    private Button confirmButton;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button back;

    private final List<MealInputGroup> mealInputs = new ArrayList<>();
    private DietBean currentDiet;

    @FXML
    public void initialize() {

        String email = SessionManagerSLT.getInstance().getLoggedmail();
        FollowDietController controller = new FollowDietController();
        currentDiet = controller.getAssignedDiet(email);

        if (currentDiet == null) {
            dietStatusLabel.setText("Scegliere una dieta prima di inserire i pasti.");
            confirmButton.setDisable(true);
            return;
        }

        //solo GUI
        dietStatusLabel.setText("Inserisci i pasti realmente assunti");
        confirmButton.setDisable(false);

        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(10));

        for (int i = 0; i < currentDiet.getGiorni().size(); i++) {
            VBox dayBox = new VBox(10);
            dayBox.setStyle("-fx-background-color: #f5e8c7; -fx-padding: 10; -fx-background-radius: 10;");
            dayBox.getChildren().add(new Label("Giorno " + (i + 1)));

            List<MealBean> meals = currentDiet.getGiorni().get(i).getPasti();
            for (MealBean meal : meals) {
                MealInputGroup inputGroup = new MealInputGroup(meal.getNome());
                mealInputs.add(inputGroup);
                dayBox.getChildren().add(inputGroup.getContainer());
            }

            mainContent.getChildren().add(dayBox);
        }

        dietInputContainer.getChildren().add(mainContent);
    }

    @FXML
    private void goback() {
        Stage stage = (Stage) back.getScene().getWindow();
        GraphicController.cambiascena(stage, "menu-view.fxml");
    }

    @FXML
    private void onConfirmMealClicked() {

        DietTakenBean dietTaken = new DietTakenBean();
        dietTaken.setUser(SessionManagerSLT.getInstance().getLoggedInUsername());

        int inputIndex = 0;

        for (int i = 0; i < currentDiet.getGiorni().size(); i++) {
            DayTakenBean dayBean = new DayTakenBean();
            dayBean.setGiorno(i + 1);

            List<MealBean> originalMeals = currentDiet.getGiorni().get(i).getPasti();

            for (int j = 0; j < originalMeals.size(); j++) {
                MealInputGroup inputGroup = mealInputs.get(inputIndex++);

                MealTakenBean mealTaken = new MealTakenBean();
                mealTaken.setNome(inputGroup.getMealName());
                mealTaken.setKcal(inputGroup.getKcal());
                mealTaken.setDescrizione(inputGroup.getDescription());

                if (mealTaken.getNome().isBlank()) {
                    continue;
                }

                dayBean.addMeal(mealTaken);
            }
            dietTaken.addDay(dayBean);
        }
        FollowDietController controller = new FollowDietController();
        controller.insertmeal(dietTaken);


    }

    //solo per prenderi i dati dalla GUI
    private static class MealInputGroup {

        private final TextField nameField;
        private final TextField kcalField;
        private final TextField descField;
        private final VBox container;

        public MealInputGroup(String defaultName) {
            this.nameField = new TextField(defaultName);
            this.nameField.setPromptText("Nome pasto");

            this.kcalField = new TextField();
            this.kcalField.setPromptText("Kcal");

            this.descField = new TextField();
            this.descField.setPromptText("Descrizione");

            this.container = new VBox(5, nameField, kcalField, descField);
            this.container.setStyle("-fx-padding: 5; -fx-background-color: #ffffff; -fx-background-radius: 5;");
        }

        public VBox getContainer() {
            return container;
        }

        public String getMealName() {
            return nameField.getText();
        }

        public int getKcal() {
            return Integer.parseInt(kcalField.getText());
        }

        public String getDescription() {
            return descField.getText();
        }
    }
}


