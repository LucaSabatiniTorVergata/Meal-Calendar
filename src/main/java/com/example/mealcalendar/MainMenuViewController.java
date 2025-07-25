package com.example.mealcalendar;


import com.example.mealcalendar.login.UserEntity;
import com.example.mealcalendar.login.UserSessionCacheSLT;
import com.example.mealcalendar.makediet.DietaEntity;
import com.example.mealcalendar.makediet.GiornoDietaEntity;
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

        if (controllo.equals(role)) {
            // Nasconde le immagini
            img1.setVisible(false);
            img2.setVisible(false);
            img3.setVisible(false);
            img4.setVisible(false);
            img5.setVisible(false);

            UserEntity user = UserSessionCacheSLT.getInstance().getUser(username);
            DietaEntity dieta = user.getDietaAssegnata();

            if (dieta != null) {
                scrollDietaPane.setVisible(true);
                dietaBox.setVisible(true);
                descrizione.setVisible(true);
                nomeDietaLabel.setText("Nome: " + dieta.getNome());
                autoreDietaLabel.setText("Autore: " + dieta.getAutore());
                durataDietaLabel.setText("Durata: " + dieta.getDurataSettimane() + " giorni");


                descrizione.setText(dieta.getDescrizione());

                StringBuilder sb = new StringBuilder();
                for (GiornoDietaEntity giorno : dieta.getGiorni()) {
                    sb.append("Giorno ").append(giorno.getNumeroGiorno()).append(":\n");
                    sb.append("- Colazione: ").append(giorno.getColazione().getNome()).append(" (").append(giorno.getColazione().getKcal()).append(" kcal)\n");
                    sb.append("- Pranzo: ").append(giorno.getPranzo().getNome()).append(" (").append(giorno.getPranzo().getKcal()).append(" kcal)\n");
                    sb.append("- Cena: ").append(giorno.getCena().getNome()).append(" (").append(giorno.getCena().getKcal()).append(" kcal)\n\n");
                }
                giorniDettagliLabel.setText(sb.toString());
            }
        }



    }




}
