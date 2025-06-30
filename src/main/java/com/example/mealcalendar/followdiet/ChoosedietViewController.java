package com.example.mealcalendar.followdiet;

import com.example.mealcalendar.GraphicController;

import com.example.mealcalendar.SessionManagerSLT;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class ChoosedietViewController {

    @FXML
    private Button back;

    @FXML
    private TableView<DietaVisualBean> dietTable;

    @FXML
    private TableColumn<DietaVisualBean, String> nomeColumn;

    @FXML
    private TableColumn<DietaVisualBean, String> autoreColumn;

    @FXML
    private TableColumn<DietaVisualBean, Integer> durataColumn;

    @FXML
    private TextArea descrizione;

    @FXML
    private Button seguiButton;

    private ObservableList<DietaVisualBean> listaDiete = FXCollections.observableArrayList();
    private DietaSubscriptionController controller;

    @FXML
    private void goback(ActionEvent event) {
        Stage stage = (Stage) back.getScene().getWindow();
        GraphicController.cambiascena(stage, "usermenu-view.fxml");
    }

    @FXML
    public void initialize() {

        controller = new DietaSubscriptionController();
        nomeColumn.setCellValueFactory(data -> data.getValue().nomeProperty());
        autoreColumn.setCellValueFactory(data -> data.getValue().autoreProperty());
        durataColumn.setCellValueFactory(data -> data.getValue().durataProperty().asObject());

        listaDiete.setAll(controller.getDieteDisponibili());
        dietTable.setItems(listaDiete);

        dietTable.setOnMouseClicked(event -> {
            DietaVisualBean selezionata = dietTable.getSelectionModel().getSelectedItem();
            if (selezionata != null) {
                descrizione.setText(selezionata.getDescrizione());
            }
        });

    }

    @FXML
    private void seguiButton(ActionEvent event) throws IOException {
        DietaVisualBean selezionata = dietTable.getSelectionModel().getSelectedItem();
        if (selezionata != null) {
            controller.assegnaDietaAUser(selezionata, SessionManagerSLT.getInstance().getLoggedInUsername());
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Dieta assegnata con successo!");
            alert.showAndWait();
        }
    }
}


