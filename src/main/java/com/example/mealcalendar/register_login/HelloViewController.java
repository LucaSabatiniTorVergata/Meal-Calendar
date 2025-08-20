package com.example.mealcalendar.register_login;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.PersistenceType;
import com.example.mealcalendar.SessionManagerSLT;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HelloViewController {

    private String restViewRegister = "/com/example/mealcalendar/registrazioneRistorante-view.fxml";
    private String userRegisterView = "/com/example/mealcalendar/register-view.fxml";

    @FXML
    private Button btnU;
    @FXML private Button btnLogin;
    @FXML private Button btnRest1;
    @FXML private Button r1;
    @FXML private Button r2;
    @FXML private Button rambutton;
    @FXML private Label messageLabel;

    @FXML
    public void useDatabase() {
        SessionManagerSLT.getInstance().setPersistenceType(PersistenceType.DATABASE);
        messageLabel.setText("Modalità: Database attiva");
    }

    @FXML
    public void useFileSystem() {
        SessionManagerSLT.getInstance().setPersistenceType(PersistenceType.FILESYSTEM);
        messageLabel.setText("Modalità: File System attiva");
    }

    @FXML
    public void useRam() {
        SessionManagerSLT.getInstance().setPersistenceType(PersistenceType.RAM);
        messageLabel.setText("Modalità: RAM attiva");
    }

    @FXML
    public void goLogin() {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        GraphicController.cambiascena(stage, "/com/example/mealcalendar/login-view.fxml");
    }

    @FXML
    public void goRestLogin() {
        SessionManagerSLT.getInstance().setRuolo("restaurant");
        Stage stage = (Stage) btnRest1.getScene().getWindow();
        GraphicController.cambiascena(stage, restViewRegister);
    }

    @FXML
    void registerUser() {
        SessionManagerSLT.getInstance().setRuolo("user");
        Stage stage = (Stage) r1.getScene().getWindow();
        stage.toFront();
        GraphicController.cambiascena(stage, userRegisterView);
    }

    @FXML
    void registerNutriz() {
        SessionManagerSLT.getInstance().setRuolo("nutritionist");
        Stage stage = (Stage) r2.getScene().getWindow(); // puoi usare un pulsante diverso se vuoi
        stage.toFront();
        GraphicController.cambiascena(stage, userRegisterView);
    }

}
