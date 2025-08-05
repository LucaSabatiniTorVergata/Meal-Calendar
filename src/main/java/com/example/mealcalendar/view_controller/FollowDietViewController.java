package com.example.mealcalendar.view_controller;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.bean.DietBean;
import com.example.mealcalendar.bean.UserBean;
import com.example.mealcalendar.controller_applicativo.FollowDietController;
import com.example.mealcalendar.dao.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.util.List;


public class FollowDietViewController {

    @FXML
    private ListView<DietBean> dietListView;
    @FXML
    private Label nameLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Button confirmButton;
    @FXML
    private Button back;

    private DietBean selectedDiet;

    private final DietDAO dietDAO = SessionManagerSLT.getInstance().getDietDAO();

    @FXML
    public void initialize() {

        List<DietBean> diets = dietDAO.getAllDiets();

        dietListView.getItems().addAll(diets);
        dietListView.setCellFactory(param -> new ListCell<>() {

            @Override
            protected void updateItem(DietBean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("📋 " + item.getNome());
                }
            }

        });

        dietListView.setOnMouseClicked(this::onDietSelected);

    }

    private void onDietSelected(MouseEvent event) {

        selectedDiet = dietListView.getSelectionModel().getSelectedItem();
        if (selectedDiet != null) {
            nameLabel.setText("Nome: " + selectedDiet.getNome());
            descriptionLabel.setText("Descrizione: " + selectedDiet.getDescrizione());
            authorLabel.setText("Autore: " + selectedDiet.getNutritionistUsername());
        }

    }

    @FXML
    private void confirmSelection() {

        UserBean userBean=new UserBean(SessionManagerSLT.getInstance().getLoggedInUsername(),SessionManagerSLT.getInstance().getLoggedmail(), SessionManagerSLT.getInstance().getLoggedRole());

        FollowDietController controller=new FollowDietController(selectedDiet,userBean);

        if (selectedDiet != null) {

            System.out.println("Hai selezionato la dieta: " + selectedDiet.getNome());
            controller.assignDiet();

        } else {

            System.out.println("Nessuna dieta selezionata.");

        }
    }

    @FXML
    private void goback() {

        Stage stage = (Stage)back .getScene().getWindow();
        GraphicController.cambiascena(stage, "menu-view.fxml");

    }
}
