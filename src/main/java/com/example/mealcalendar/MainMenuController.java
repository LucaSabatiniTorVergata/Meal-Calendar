package com.example.mealcalendar;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;





public class MainMenuController {

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


    //metodi eseguibi dal guest
    @FXML
    private void findRestaurantGuest(ActionEvent event) {
        Stage stage = (Stage) findrestaurantbutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "findrestaurantguest-view.fxml");
    }

    @FXML
    private void goHome(ActionEvent event) throws IOException {
        Stage stage = (Stage) homebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "hello-view.fxml");
    }

    //metodi eseguibili dall'user
    @FXML
    private void findRestaurantUser(ActionEvent event) throws IOException {

        Stage stage = (Stage) findrestaurantbutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "findrestaurantuser-view.fxml");
    }

    @FXML
    private void loadCalendarMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) seteatingtimebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "mealcalendar-view.fxml");
    }

    @FXML
    private void loadFindRecpe(ActionEvent event) throws IOException {
        Stage stage = (Stage) findrecipebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "recipe-view.fxml");
    }

    @FXML
    private void loadFrigeMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) fillfridgebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "fridge-view.fxml");
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        SessionManagerSLT.getInstance().logout();
        Stage stage = (Stage) backbutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "login-view.fxml");
    }

    @FXML
    public void initialize() throws IOException {
        String username = SessionManagerSLT.getInstance().getLoggedInUsername();
        if (username != null) {
            welcomelabel.setText("Hi, " + username + "!");
        }
    }
}