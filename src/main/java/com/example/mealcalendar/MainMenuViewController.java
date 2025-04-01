package com.example.mealcalendar;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


public class MainMenuViewController {

    @FXML
    private Button backbutton;

    @FXML
    private Button fillfridgebutton;

    @FXML
    private Button findrecipebutton;

    @FXML
    private Button findrestaurantbutton;

    @FXML
    private Button seteatingtimebutton;

    @FXML
    private Button homebutton;

    @FXML
    private Label welcomelabel;

    // Aggiungi un flag per la persistenza, per esempio


    //metodi eseguibili dal guest
    @FXML
    private void findRestaurantGuest(ActionEvent event) {
        Stage stage = (Stage) findrestaurantbutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "findrestaurantguest-view.fxml");
    }

    @FXML
    private void goHome(ActionEvent event) {
        Stage stage = (Stage) homebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "hello-view.fxml");
    }

    //metodi eseguibili dall'user
    @FXML
    private void findRestaurantUser(ActionEvent event)  {
        Stage stage = (Stage) findrestaurantbutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "findrestaurantuser-view.fxml");
    }

    @FXML
    private void loadCalendarMenu(ActionEvent event)  {
        Stage stage = (Stage) seteatingtimebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "mealcalendar-view.fxml");
    }

    @FXML
    private void loadFindRecpe(ActionEvent event) {
        Stage stage = (Stage) findrecipebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "recipe-view.fxml");
    }

    @FXML
    private void loadFrigeMenu(ActionEvent event) {
        Stage stage = (Stage) fillfridgebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "fridge-view.fxml");
    }

    @FXML
    private void goBack(ActionEvent event) {
        SessionManagerSLT.getInstance().logout();
        Stage stage = (Stage) backbutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "login-view.fxml");

    }

    @FXML
    public void initialize()  {
        String username = SessionManagerSLT.getInstance().getLoggedInUsername();
        if (username != null) {
            welcomelabel.setText("Hi, " + username + "!");
        }
    }




}
