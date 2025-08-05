package com.example.mealcalendar.view_controller;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.bean.DayBean;
import com.example.mealcalendar.bean.DietBean;
import com.example.mealcalendar.bean.MealBean;
import com.example.mealcalendar.controller_applicativo.DietCreationController;
import com.example.mealcalendar.SessionManagerSLT;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AddDietViewController {

    @FXML private TextField nomedieta;
    @FXML private TextArea descrizione;
    @FXML private SplitMenuButton duratadieta;
    @FXML private MenuItem durata7;
    @FXML private MenuItem durata14;
    @FXML private VBox colonnaSinistra;
    @FXML private VBox colonnaDestra;
    @FXML private Button backbutton;

    private int durata = 0;
    private final List<GridPane> giorniPannelli = new ArrayList<>();

    @FXML
    public void initialize() {
        durata7.setOnAction(e -> {
            durata = 7;
            duratadieta.setText("7 giorni");
        });
        durata14.setOnAction(e -> {
            durata = 14;
            duratadieta.setText("14 giorni");
        });
    }

    @FXML
    private void makeschedule() {
        colonnaSinistra.getChildren().clear();
        colonnaDestra.getChildren().clear();
        giorniPannelli.clear();

        for (int i = 0; i < durata; i++) {
            GridPane giornoPane = new GridPane();
            giornoPane.setHgap(10);
            giornoPane.setVgap(5);

            Label giornoLabel = new Label("Giorno " + (i + 1));
            giornoPane.add(giornoLabel, 0, 0);

            // Creiamo 3 pasti: riga 1, 2, 3 (a partire da riga 1 perché 0 è il label del giorno)
            for (int pastoIndex = 0; pastoIndex < 3; pastoIndex++) {
                TextField nomePasto = new TextField();
                nomePasto.setPromptText("Nome pasto");

                TextField kcalPasto = new TextField();
                kcalPasto.setPromptText("Kcal");

                TextField descrizionePasto = new TextField();
                descrizionePasto.setPromptText("Descrizione");

                int row = pastoIndex + 1;

                giornoPane.add(nomePasto, 0, row);
                giornoPane.add(kcalPasto, 1, row);
                giornoPane.add(descrizionePasto, 2, row);
            }

            giorniPannelli.add(giornoPane);

            if (i % 2 == 0) {
                colonnaSinistra.getChildren().add(giornoPane);
            } else {
                colonnaDestra.getChildren().add(giornoPane);
            }
        }
    }


    @FXML
    private void confirm() {

        String nome = nomedieta.getText();
        String desc = descrizione.getText();



        DietBean dietBean = new DietBean();
        dietBean.setNome(nome);
        dietBean.setDescrizione(desc);
        dietBean.setDurata(durata);
        dietBean.setNutritionistUsername(SessionManagerSLT.getInstance().getLoggedInUsername());

        for (int i = 0; i < durata; i++) {

            GridPane giornoPane = giorniPannelli.get(i);
            TextField nomeField = (TextField) giornoPane.getChildren().get(1);
            TextField kcalField = (TextField) giornoPane.getChildren().get(2);
            TextField descField = (TextField) giornoPane.getChildren().get(3);

            MealBean mealBean = new MealBean();
            mealBean.setNome(nomeField.getText());
            mealBean.setDescrizione(descField.getText());
            try {
                mealBean.setKcal(Integer.parseInt(kcalField.getText()));
            } catch (NumberFormatException e) {
                return;
            }

            DayBean dayBean = new DayBean();
            dayBean.setGiorno(i + 1);
            dayBean.addMeal(mealBean);
            dietBean.addDay(dayBean);
        }

        // Salvataggio tramite controller
        DietCreationController controller = new DietCreationController();
        controller.saveDiet(dietBean);

        clean();
    }

    @FXML
    private void clean() {
        nomedieta.clear();
        descrizione.clear();
        duratadieta.setText("Scegli durata");
        durata = 0;
        giorniPannelli.clear();
        colonnaSinistra.getChildren().clear();
        colonnaDestra.getChildren().clear();
    }

    @FXML
    private void goBack() {

        Stage stage = (Stage) backbutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "menu-view.fxml");
    }


}