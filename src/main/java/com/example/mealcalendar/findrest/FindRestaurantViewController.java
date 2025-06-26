package com.example.mealcalendar.findrest;

import com.example.mealcalendar.*;
import com.example.mealcalendar.seteatingtime.MealCalenderViewController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.function.UnaryOperator;

import static com.example.mealcalendar.seteatingtime.MealCalenderViewController.*;


public class FindRestaurantViewController {

    private static final boolean DEBUG = true;

    private static final Logger LOGGER = Logger.getLogger(FindRestaurantViewController.class.getName());

    @FXML
    private Label lableemail;

    @FXML
    private ProgressIndicator loadingIndicator;

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

        tipoDietaSelezionato = tipoDieta.getText();


        pastoSelezionato = pasto.getText();
        distanzaInserita = Double.parseDouble(distanza.getText());

        LOGGER.log(Level.INFO, "Filtri confermati:");
        LOGGER.log(Level.INFO, "Dieta: {0}", tipoDietaSelezionato);
        LOGGER.log(Level.INFO, "Pasto: {0}", pastoSelezionato);
        LOGGER.log(Level.INFO, "Distanza: {0} km", distanzaInserita);

        FiltersRestaurantBean filtro = new FiltersRestaurantBean();
        filtro.setPasto(pastoSelezionato);
        filtro.setDistanza(distanzaInserita);
        filtro.setTipoDieta(tipoDietaSelezionato);
        ChooseRestaurantController controller = new ChooseRestaurantController(filtro,new FindRestaurantApiBoundary());
        List<ReturnRestaurantsBean> ristorantiBeans = controller.trovaRistorante();
        mostraRistoranti(ristorantiBeans);
    }

    @FXML
    public void initialize() {
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
    private void handleclick(MouseEvent event) {
        if (event.getClickCount() != 2) {
            return;
        }
        int selectedIndex = ristorantiListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            return;
        }
        ReturnRestaurantsBean ristorante = listaRistoranti.get(selectedIndex);
        if (isVengoDaCalendar()) {
            processCalendarSelection(ristorante);
        } else {
            loadingIndicator.setVisible(false);
            apriGoogleMaps(ristorante.getLatitudine(), ristorante.getLongitudine());
        }
    }

    private void processCalendarSelection(ReturnRestaurantsBean ristorante) {
        if (!DEBUG) {
            // In modalità produzione, non mostrare il progresso di caricamento
            return;
        }

        lableemail.setVisible(true);
        loadingIndicator.setVisible(true);
        loadingIndicator.setProgress(0);
        final Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
            private double progress = 0;
            @Override
            public void handle(ActionEvent event) {
                progress += 0.05;
                loadingIndicator.setProgress(progress);
                if (progress >= 1) {
                    timeline.stop();
                    try {
                        lableemail.setVisible(false);
                        // Resetto la modalità calendario e imposto il ristorante selezionato
                        setVengoDaCalendar(false);
                        setRistorantescelto("🍽️ " + ristorante.getNome() + " - 📍 " + ristorante.getIndirizzo());
                        MealCalenderViewController.inviomail();  // Chiamata al metodo che invia l'email
                        Stage stage = (Stage) seteatingtimebutton.getScene().getWindow();
                        GraphicController.cambiascena(stage, "mealcalendar-view.fxml");
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, "Errore nel processo", ex);
                    }
                }
            }
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void registerGuest(ActionEvent event)  {
        Stage stage = (Stage) registerbutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "register-view.fxml");
    }

    @FXML
    private void loadHomePageGuest(ActionEvent event) {
        Stage stage = (Stage) homebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "guestmenu-view.fxml");
    }

    @FXML
    private void loadHomePageUser(ActionEvent event) {
        Stage stage = (Stage) homebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "usermenu-view.fxml");
    }

    @FXML
    private void recipeuser(ActionEvent event) {
        Stage stage = (Stage) findrecipebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "recipe-view.fxml");
    }

    @FXML
    private void fridgeuser(ActionEvent event) {
        Stage stage = (Stage) fillfridgebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "fridge-view.fxml");
    }

    @FXML
    private void calendaruser(ActionEvent event) {
        Stage stage = (Stage) seteatingtimebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "mealcalendar-view.fxml");
    }
}
