package com.example.mealcalendar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {

    // Istanza di AntiCodeSmellPrinter per l'output
    private static final AntiCodeSmellPrinter printer = new AntiCodeSmellPrinter("Main");

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setResizable(true);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Calendario dei Pasti");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        printer.print("===== Calendario dei Pasti =====");
        printer.print("1. Avvia interfaccia grafica (JavaFX)");
        printer.print("2. Avvia interfaccia a linea di comando (CLI)");
        printer.print("Scegli un'opzione: ");
        String choice = scanner.nextLine();

        if ("2".equals(choice)) {
            HelloViewBoundaryCli cli = new HelloViewBoundaryCli();
            cli.start();
        } else {
            launch();
        }
    }
}
