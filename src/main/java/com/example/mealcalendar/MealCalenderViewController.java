package com.example.mealcalendar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.util.Callback;

public class MealCalenderViewController {

    private static final Logger LOGGER = Logger.getLogger(MealCalenderViewController.class.getName());

    @FXML
    private Button backhome;

    @FXML
    private SplitMenuButton posizione;

    @FXML
    private DatePicker calendar;

    @FXML
    private Button confirmButton;

    @FXML
    private MenuItem home;

    @FXML
    private MenuItem restaurant;

    @FXML
    private TextField orascelta;

    private static boolean sceltaLuogo = false;
    private static boolean vengoDaCalendar = false;
    private static String ristorantescelto;
    private static String ricettascelta;
    private LocalDate dataselezionata;
    private String oraselezionata;

    @FXML
    private void initialize() {
        calendar.setDayCellFactory(disablePastDates());

        home.setOnAction(e -> {
            posizione.setText("home");
            setSceltaLuogo(false);
        });

        restaurant.setOnAction(e -> {
            posizione.setText("restaurant");
            setSceltaLuogo(true);
        });

        Pattern pattern = Pattern.compile("(\\d{1,2})?:?(\\d{1,2})?");
        UnaryOperator<TextFormatter.Change> filter = change -> pattern.matcher(change.getControlNewText()).matches() ? change : null;
        orascelta.setTextFormatter(new TextFormatter<>(filter));

        LOGGER.log(Level.INFO, "Scelta luogo: {0}", sceltaLuogo);
    }

    @FXML
    private void trick(ActionEvent actionEvent){
        confirmChoise();
    }

    private void confirmChoise() {
        SessionManagerSLT.getInstance().setDatas(calendar.getValue());
        SessionManagerSLT.getInstance().setOras(orascelta.getText());
        setVengoDaCalendar(true);
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        GraphicController.cambiascena(stage, sceltaLuogo ? "findrestaurantuser-view.fxml" : "recipe-view.fxml");
    }

    public static void inviomail() {
        MealcalendarBean bean = new MealcalendarBean(SessionManagerSLT.getInstance().getDatas(), SessionManagerSLT.getInstance().getOras(), SessionManagerSLT.getInstance().getLoggedInUsername(),
                sceltaLuogo ? getRistorantescelto() : getRicettascelta());
        new MealcalendarController(bean).invioMail();
    }

    @FXML
    private void homeview(ActionEvent event) {
        Stage stage = (Stage) backhome.getScene().getWindow();
        GraphicController.cambiascena(stage, "usermenu-view.fxml");
    }

    private Callback<DatePicker, DateCell> disablePastDates() {
        return datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffaaaa;");
                }
            }
        };
    }

    // Getter e Setter per variabili statiche
    public static boolean isSceltaLuogo() {
        return sceltaLuogo;
    }

    public static void setSceltaLuogo(boolean scelta) {
        sceltaLuogo = scelta;
    }

    public static boolean isVengoDaCalendar() {
        return vengoDaCalendar;
    }

    public static void setVengoDaCalendar(boolean value) {
        vengoDaCalendar = value;
    }

    public static String getRistorantescelto() {
        return ristorantescelto;
    }

    public static void setRistorantescelto(String value) {
        ristorantescelto = value;
    }

    public static String getRicettascelta() {
        return ricettascelta;
    }

    public static void setRicettascelta(String value) {
        ricettascelta = value;
    }
}
