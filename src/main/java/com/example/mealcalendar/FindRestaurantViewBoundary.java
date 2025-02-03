package com.example.mealcalendar;

import javafx.scene.input.MouseEvent;
import java.io.IOException;
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
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javafx.scene.control.ListView;
import java.util.regex.Pattern;
import javafx.scene.control.TextFormatter;
import java.util.function.UnaryOperator;


public class FindRestaurantViewBoundary {

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
    private ListView<ReturnRestaurantsBean> ristorantiListView;// ListView per mostrare i ristoranti

    private String tipoDietaSelezionato;
    private String pastoSelezionato;
    private double distanzaInserita;

    private List<ReturnRestaurantsBean> listaRistoranti;


    @FXML
    private void loadHomePageGuest(ActionEvent event) throws IOException {
        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("guestmenu-view.fxml"));
        Scene nuovaScena = new Scene(nuovaSchermata);
        Stage finestra = (Stage) ((Node) event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
        finestra.show();

    }

    @FXML
    private void registerGuest(ActionEvent event) throws IOException {
        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene nuovaScena = new Scene(nuovaSchermata);
        Stage finestra = (Stage) ((Node) event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
        finestra.show();

    }

    @FXML
    private void loadHomePageUser(ActionEvent event) throws IOException {
        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("usermenu-view.fxml"));
        Scene nuovaScena = new Scene(nuovaSchermata);
        Stage finestra = (Stage) ((Node) event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
        finestra.show();
    }

    @FXML
    private void recipeuser(ActionEvent event) throws IOException {
        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("recipe-view.fxml"));
        Scene nuovaScena = new Scene(nuovaSchermata);
        Stage finestra = (Stage) ((Node) event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
        finestra.show();
    }

    @FXML
    private void fridgeuser(ActionEvent event) throws IOException {
        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("fridge-view.fxml"));
        Scene nuovaScena = new Scene(nuovaSchermata);
        Stage finestra = (Stage) ((Node) event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
        finestra.show();
    }

    @FXML
    private void calendaruser(ActionEvent event) throws IOException {
        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("mealcalendar-view.fxml"));
        Scene nuovaScena = new Scene(nuovaSchermata);
        Stage finestra = (Stage) ((Node) event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
        finestra.show();
    }

    @FXML
    private void confermafiltri(ActionEvent event) throws IOException {
        // Solo dopo aver premuto "conferma" copiamo i dati
        if(tipoDieta.getText().equalsIgnoreCase("Onnivoro")){
            tipoDietaSelezionato = "";

        }
        else {
            tipoDietaSelezionato = tipoDieta.getText();
        }

        pastoSelezionato = pasto.getText();
        distanzaInserita= Double.parseDouble(distanza.getText());
        System.out.println("Filtri confermati:");
        System.out.println("Dieta: " + tipoDietaSelezionato);
        System.out.println("Pasto: " + pastoSelezionato);
        System.out.println("Distanza: " + distanzaInserita + " km");
        FiltersRestaurantBean filtro = new FiltersRestaurantBean(tipoDietaSelezionato, pastoSelezionato, distanzaInserita);

        ChooseRestaurantController Controller = new ChooseRestaurantController(filtro);
        List<ReturnRestaurantsBean> RistorantiBeans = Controller.trovaRistorante();

        mostraRistoranti(RistorantiBeans);;

    }

    @FXML
    public void initialize() throws IOException {
        // Associare le azioni ai MenuItem (cambiano il testo del bottone)
        vegan.setOnAction(e -> tipoDieta.setText("Vegano"));
        vegetariana.setOnAction(e -> tipoDieta.setText("Vegetariano"));
        omnivora.setOnAction(e -> tipoDieta.setText("Onnivoro"));

        colazione.setOnAction(e -> pasto.setText("Colazione"));
        pranzo.setOnAction(e -> pasto.setText("Pranzo"));
        cena.setOnAction(e -> pasto.setText("Cena"));
        Pattern validEditingState = Pattern.compile("\\d*"); // Solo cifre

        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (validEditingState.matcher(change.getControlNewText()).matches()) {
                return change; // Accetta la modifica
            } else {
                return null; // Rifiuta la modifica se contiene caratteri non numerici
            }
        };

        TextFormatter<String> textformatter = new TextFormatter<>(filter);
        distanza.setTextFormatter(textformatter);
    }
    //Metodo per mostrare i ristoranti nella ListView
    public void mostraRistoranti(List<ReturnRestaurantsBean> ListaRistoranti) {
        ristorantiListView.getItems().clear();
        this.listaRistoranti=ListaRistoranti;
        ristorantiListView.getItems().addAll(ListaRistoranti);
    }
    //Metodo per aprire Google Maps nel browser con le coordinate
        private void apriGoogleMaps(double lat, double lng) {
            String url = "https://www.google.com/maps/search/?api=1&query=" + lat + "," + lng;
            try {
                Desktop.getDesktop().browse(new URI(url)); // Apriamo il browser
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }


    //Metodo chiamato quando l'utente clicca su un ristorante
    @FXML
    private void handleclick(MouseEvent event) {
        if (event.getClickCount() == 2) { // Doppio click per aprire Google Maps
            int selectedIndex = ristorantiListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                ReturnRestaurantsBean Ristorante = listaRistoranti.get(selectedIndex);
                apriGoogleMaps(Ristorante.getLatitudine(), Ristorante.getLongitudine());
            }
        }
    }



}

