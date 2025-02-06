package com.example.mealcalendar;

import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javafx.scene.control.ListView;
import java.util.regex.Pattern;
import javafx.scene.control.TextFormatter;
import java.util.function.UnaryOperator;
import javafx.scene.control.Label;

public class FindRestaurantViewBoundary {

    private static final Logger LOGGER = Logger.getLogger(FindRestaurantViewBoundary.class.getName());

    @FXML
    private SplitMenuButton tipoDieta;
    @FXML
    private SplitMenuButton pasto;
    @FXML
    private TextField distanza;

    @FXML
    private MenuItem vegan;
    @FXML
    private MenuItem vegetariana;
    @FXML
    private MenuItem omnivora;

    @FXML
    private MenuItem colazione;
    @FXML
    private MenuItem pranzo;
    @FXML
    private MenuItem cena;

    @FXML
    private Label welcomelabel;

    @FXML
    private ListView<ReturnRestaurantsBean> ristorantiListView;

    @FXML
    private Button homebutton;

    @FXML
    private Button registerbutton;

    @FXML
    private Button findrecipebutton;
    @FXML
    private Button seteatingtimebutton;
    @FXML
    private Button fillfridgebutton;

    private String tipoDietaSelezionato;
    private String pastoSelezionato;
    private double distanzaInserita;

    private List<ReturnRestaurantsBean> listaRistoranti;

    @FXML
    private void confermafiltri(ActionEvent event) throws IOException {
        if (tipoDieta.getText().equalsIgnoreCase("Onnivoro")) {
            tipoDietaSelezionato = "";
        } else {
            tipoDietaSelezionato = tipoDieta.getText();
        }

        pastoSelezionato = pasto.getText();
        distanzaInserita = Double.parseDouble(distanza.getText());

        LOGGER.log(Level.INFO, "Filtri confermati:");
        LOGGER.log(Level.INFO, "Dieta: {0}", tipoDietaSelezionato);
        LOGGER.log(Level.INFO, "Pasto: {0}", pastoSelezionato);
        LOGGER.log(Level.INFO, "Distanza: {0} km", distanzaInserita);

        FiltersRestaurantBean filtro = new FiltersRestaurantBean(tipoDietaSelezionato, pastoSelezionato, distanzaInserita);
        ChooseRestaurantController controller = new ChooseRestaurantController(filtro);
        List<ReturnRestaurantsBean> ristorantiBeans = controller.trovaRistorante();
        mostraRistoranti(ristorantiBeans);
    }

    @FXML
    public void initialize() throws IOException {

        String username = SessionManagerSLT.getInstance().getLoggedInUsername();
        if (username != null) {
            welcomelabel.setText("Hi, " + username + "!");
        }

        vegan.setOnAction(e -> tipoDieta.setText("Vegano"));
        vegetariana.setOnAction(e -> tipoDieta.setText("Vegetariano"));
        omnivora.setOnAction(e -> tipoDieta.setText("Onnivoro"));

        colazione.setOnAction(e -> pasto.setText("Colazione"));
        pranzo.setOnAction(e -> pasto.setText("Pranzo"));
        cena.setOnAction(e -> pasto.setText("Cena"));

        Pattern validEditingState = Pattern.compile("\\d*");
        UnaryOperator<TextFormatter.Change> filter = change -> validEditingState.matcher(change.getControlNewText()).matches() ? change : null;

        TextFormatter<String> textformatter = new TextFormatter<>(filter);
        distanza.setTextFormatter(textformatter);
    }

    public void mostraRistoranti(List<ReturnRestaurantsBean> listaRistoranti) {
        ristorantiListView.getItems().clear();
        this.listaRistoranti = listaRistoranti;
        ristorantiListView.getItems().addAll(listaRistoranti);
    }

    private void apriGoogleMaps(double lat, double lng) {
        String url = "https://www.google.com/maps/search/?api=1&query=" + lat + "," + lng;
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            LOGGER.log(Level.SEVERE, "Errore nell'apertura di Google Maps", e);
        }
    }

    @FXML
    private void registerGuest(ActionEvent event) throws IOException {
        Stage stage = (Stage)registerbutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "hello-view.fxml");
    }


    @FXML
    private void loadHomePageGuest(ActionEvent event) {
        Stage stage = (Stage)homebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "guestmenu-view.fxml");
    }

    @FXML
    private void loadHomePageUser(ActionEvent event) throws IOException {
        Stage stage = (Stage)homebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "usermenu-view.fxml");
    }


    @FXML
    private void recipeuser(ActionEvent event) throws IOException {
        Stage stage = (Stage)findrecipebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "recipelist-view.fxml");
    }

    @FXML
    private void fridgeuser(ActionEvent event) throws IOException {
        Stage stage = (Stage)fillfridgebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "fridge-view.fxml");
    }

    @FXML
    private void calendaruser(ActionEvent event) throws IOException {
        Stage stage = (Stage)seteatingtimebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "mealcalendar-view.fxml");
    }

    @FXML
    private void handleclick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            int selectedIndex = ristorantiListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                ReturnRestaurantsBean ristorante = listaRistoranti.get(selectedIndex);
                apriGoogleMaps(ristorante.getLatitudine(), ristorante.getLongitudine());
            }
        }
    }
}
