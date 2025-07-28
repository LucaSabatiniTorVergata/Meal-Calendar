package com.example.mealcalendar;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import java.io.IOException;


public class MainMenuViewController {

    @FXML
    private Button backbutton;

    @FXML
    private Button insertmealbutton;

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

    @FXML
    private Label rolelabel;

    @FXML
    private Button makediet;

    @FXML
    private Button choose;

    @FXML
    private VBox dietaBox;
    @FXML
    private Label titoloDietaLabel;
    @FXML
    private Label nomeDietaLabel;
    @FXML
    private Label autoreDietaLabel;
    @FXML
    private Label durataDietaLabel;
    @FXML
    private Label giorniDettagliLabel;

    @FXML
    private ImageView img1;

    @FXML
    private ImageView img2;

    @FXML
    private ImageView img3;

    @FXML
    private ImageView img4;

    @FXML
    private ImageView img5;

    @FXML
    private ScrollPane scrollDietaPane;

    @FXML
    private TextArea descrizione;

    private String controllo="utente";

    //metodi eseguibili dal guest
    @FXML
    private void findRestaurantGuest(ActionEvent event) {
        Stage stage = (Stage) findrestaurantbutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "findrestaurantguest-view.fxml");
    }

    @FXML
    private void goHome(ActionEvent event) {
        Stage stage = (Stage) homebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "register-view.fxml");
    }

    @FXML
    private void gomakediet(ActionEvent event) {
        if(SessionManagerSLT.getInstance().getLoggedRole().equals("nutrizionista")){
        Stage stage = (Stage) makediet.getScene().getWindow();
        GraphicController.cambiascena(stage, "makediet-view.fxml");
        }
    }

    @FXML
    private void gochooseview(ActionEvent event) {

        if(SessionManagerSLT.getInstance().getLoggedRole().equals(controllo)){
            Stage stage = (Stage) choose.getScene().getWindow();
            GraphicController.cambiascena(stage, "choosediet-view.fxml");
        }
    }

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
    private void loadInsertMeal(ActionEvent event) {
        if(SessionManagerSLT.getInstance().getLoggedRole().equals(controllo)){
         Stage stage = (Stage)insertmealbutton .getScene().getWindow();
         GraphicController.cambiascena(stage, "insertmeal-view.fxml");
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        SessionManagerSLT.getInstance().logout();
        Stage stage = (Stage) backbutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "login-view.fxml");

    }

    @FXML
    public void initialize() throws IOException {
        String username = SessionManagerSLT.getInstance().getLoggedInUsername();
        String role=SessionManagerSLT.getInstance().getLoggedRole();
        if (username != null) {
            welcomelabel.setText("Ciao, " + username + "!");
        }
        if (role != null) {
            rolelabel.setText("Sei un "+role);
        }




    }




}
