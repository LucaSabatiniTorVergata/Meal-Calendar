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


public class FindRestaurantBoundary {

    @FXML
    private SplitMenuButton TipoDieta;
    @FXML
    private SplitMenuButton Pasto;
    @FXML
    private TextField Distanza;

    @FXML
    private MenuItem Vegan;
    @FXML
    private MenuItem Vegetariana;
    @FXML
    private MenuItem Omnivora;

    @FXML
    private MenuItem Colazione;
    @FXML
    private MenuItem Pranzo;
    @FXML
    private MenuItem Cena;

    @FXML
    private ListView<ReturnRestaurantsBean> RistorantiListView;// ListView per mostrare i ristoranti

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
        if(TipoDieta.getText().equalsIgnoreCase("Onnivoro")){
            tipoDietaSelezionato = "";

        }
        else {
            tipoDietaSelezionato = TipoDieta.getText();
        }
        pastoSelezionato = Pasto.getText();
        distanzaInserita= Double.parseDouble(Distanza.getText());
        System.out.println("Filtri confermati:");
        System.out.println("Dieta: " + tipoDietaSelezionato);
        System.out.println("Pasto: " + pastoSelezionato);
        System.out.println("Distanza: " + distanzaInserita + " km");
        FiltersRestaurantBean filtro = new FiltersRestaurantBean(tipoDietaSelezionato, pastoSelezionato, distanzaInserita);

        ChooseRestaurantController Controller = new ChooseRestaurantController(filtro);
        List<ReturnRestaurantsBean> RistorantiBeans = Controller.trovaRistorante();

        MostraRistoranti(RistorantiBeans);;

    }

    @FXML
    public void initialize() throws IOException {
        // Associare le azioni ai MenuItem (cambiano il testo del bottone)
        Vegan.setOnAction(e -> TipoDieta.setText("Vegano"));
        Vegetariana.setOnAction(e -> TipoDieta.setText("Vegetariano"));
        Omnivora.setOnAction(e -> TipoDieta.setText("Onnivoro"));

        Colazione.setOnAction(e -> Pasto.setText("Colazione"));
        Pranzo.setOnAction(e -> Pasto.setText("Pranzo"));
        Cena.setOnAction(e -> Pasto.setText("Cena"));
        Pattern validEditingState = Pattern.compile("\\d*"); // Solo cifre

        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (validEditingState.matcher(change.getControlNewText()).matches()) {
                return change; // Accetta la modifica
            } else {
                return null; // Rifiuta la modifica se contiene caratteri non numerici
            }
        };

        TextFormatter<String> textformatter = new TextFormatter<>(filter);
        Distanza.setTextFormatter(textformatter);
    }
    //Metodo per mostrare i ristoranti nella ListView
    public void MostraRistoranti(List<ReturnRestaurantsBean> ListaRistoranti) {
        RistorantiListView.getItems().clear();
        this.listaRistoranti=ListaRistoranti;
        RistorantiListView.getItems().addAll(ListaRistoranti);
    }
    //Metodo per aprire Google Maps nel browser con le coordinate
        private void ApriGoogleMaps(double lat, double lng) {
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
            int selectedIndex = RistorantiListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                ReturnRestaurantsBean Ristorante = listaRistoranti.get(selectedIndex);
                ApriGoogleMaps(Ristorante.getLatitudine(), Ristorante.getLongitudine());
            }
        }
    }



}

