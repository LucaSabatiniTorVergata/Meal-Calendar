package com.example.mealcalendar.view_controller;


import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.dao.DietDAO;
import com.example.mealcalendar.dao.ReportRequestDAO;
import com.example.mealcalendar.dao.UserDietDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;



public class MainMenuViewController {

    @FXML
    private Button reports;

    @FXML
    private Button requests;

    @FXML
    private Button backbutton;

    @FXML
    private Button followdietbutton;

    @FXML
    private Button findrecipebutton;

    @FXML
    private Button findrestaurantbutton;

    @FXML
    private Button reservationButton;

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
    private Button insertmeal;

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

    private String controllo="user";

    //metodi eseguibili dal guest

    @FXML
    private void loadReservationPage(ActionEvent event) {

        Stage stage = (Stage) reservationButton.getScene().getWindow();
        GraphicController.cambiascena(stage, "PrenotazioneUserView.fxml");
    }


    @FXML
    private void gomakediet(ActionEvent event) {

        if(SessionManagerSLT.getInstance().getLoggedRole().equals("nutritionist")){
        Stage stage = (Stage) makediet.getScene().getWindow();
        GraphicController.cambiascena(stage, "makediet-view.fxml");
        }

    }

    @FXML
    private void goinsertmealview(ActionEvent event) {

        if(SessionManagerSLT.getInstance().getLoggedRole().equals(controllo)){
            Stage stage = (Stage) insertmeal.getScene().getWindow();
            GraphicController.cambiascena(stage, "insertmeal-view.fxml");
        }
    }

    @FXML
    private void findRestaurantUser(ActionEvent event)  {
        Stage stage = (Stage) findrestaurantbutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "prenotaRistorante-view.fxml");
    }

    @FXML
    private void loadCalendarMenu(ActionEvent event)  {
        Stage stage = (Stage) seteatingtimebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "mealcalendar-view.fxml");
    }


    @FXML
    private void followdiet(ActionEvent event) {

        if(SessionManagerSLT.getInstance().getLoggedRole().equals(controllo)){
         Stage stage = (Stage)followdietbutton .getScene().getWindow();
         GraphicController.cambiascena(stage, "followdiet-view.fxml");
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        SessionManagerSLT.getInstance().logout();
        Stage stage = (Stage) backbutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "login-view.fxml");

    }

    @FXML
    private void viewreports(){

        if(SessionManagerSLT.getInstance().getLoggedRole().equals(controllo)){
            Stage stage = (Stage) reports.getScene().getWindow();
            GraphicController.cambiascena(stage, "usereport-view.fxml");
        }

    }

    @FXML
    private void viewrequests(){

        if(SessionManagerSLT.getInstance().getLoggedRole().equals("nutritionist")){
            Stage stage = (Stage)requests.getScene().getWindow();
            GraphicController.cambiascena(stage, "vieweansbynutri-view.fxml");
        }

    }


    @FXML
    public void initialize() {

        ReportRequestDAO.getInstance().getAll();
        UserDietDAO.getInstance().getAllUsers();
        //DietDAO.getInstance().getAllDiets();
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
