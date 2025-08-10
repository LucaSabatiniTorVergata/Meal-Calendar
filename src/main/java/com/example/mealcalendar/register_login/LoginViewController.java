package com.example.mealcalendar.register_login;

import com.example.mealcalendar.GraphicController;

import com.example.mealcalendar.SessionManagerSLT;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginViewController {

    @FXML private TextField user;
    @FXML private PasswordField pass;
    @FXML private SplitMenuButton ruolo;
    @FXML private MenuItem nutrizionista;
    @FXML private MenuItem ristorante;
    @FXML private MenuItem utente;
    @FXML private Label messagelabel;
    @FXML private Button login;
    @FXML private Button back;

    private String selectedRole = "";

    @FXML
    private void initialize() {

        nutrizionista.setOnAction(e -> {
            ruolo.setText("Nutrizionista");
            selectedRole = "nutritionist";
        });

        utente.setOnAction(e -> {
            ruolo.setText("Utente");
            selectedRole = "user";
        });
        ristorante.setOnAction(e -> {
            ruolo.setText("ristorante");
            selectedRole = "restaurant";
        });
    }

    @FXML
    public void logIn() {

        String username = user.getText();
        String password = pass.getText();

        if (username.isEmpty() || password.isEmpty() || selectedRole.isEmpty()) {
            messagelabel.setText("Compila tutti i campi.");
            return;
        }

        UserLoginBean bean = new UserLoginBean(username, password, selectedRole);
        LoginController controller = new LoginController();

        try {
            if (controller.vallogin(bean)) {
                messagelabel.setText("Login effettuato!");

                if(SessionManagerSLT.getInstance().getRuolo().equalsIgnoreCase("restaurant")){

                    Stage stage = (Stage) login.getScene().getWindow();
                    GraphicController.cambiascena(stage, "/com/example/mealcalendar/restaurantManagement-view.fxml");
                }

                Stage stage = (Stage) login.getScene().getWindow();
                GraphicController.cambiascena(stage, "/com/example/mealcalendar/menu-view.fxml");


            } else {
                messagelabel.setText("Credenziali non valide.");
            }
        } catch (Exception e) {
            messagelabel.setText("Errore durante il login.");
        }
    }

    @FXML
    public void goback() {

        Stage stage = (Stage)back.getScene().getWindow();
        GraphicController.cambiascena(stage, "/com/example/mealcalendar/hello-view.fxml");
    }
}