package com.example.mealcalendar;

import com.example.mealcalendar.cli.HelloViewCLI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Scanner;


public class Main extends Application {

    private static final AntiCodeSmellPrinter printer = new AntiCodeSmellPrinter("Main");

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage.setTitle("Meal Calendar");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

        printer.print("===== Calendario dei Pasti =====");
        printer.print("1. Avvia interfaccia grafica (JavaFX)");
        printer.print("2. Avvia interfaccia a linea di comando (CLI)");
        printer.print("Scegli un'opzione: ");

        String scelta = scanner.nextLine().trim();

        if (scelta.equals("1")) {
            launch(args);
        } else {
            HelloViewCLI cli = new HelloViewCLI(scanner);
            cli.start();
        }
    }

}
