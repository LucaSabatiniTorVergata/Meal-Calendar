package com.example.mealcalendar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {

    private static Stage stg;

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
        System.out.println("===== Calendario dei Pasti =====");
        System.out.println("1. Avvia interfaccia grafica (JavaFX)");
        System.out.println("2. Avvia interfaccia a linea di comando (CLI)");
        System.out.print("Scegli un'opzione: ");
        String choice = scanner.nextLine();

        if ("2".equals(choice)) {
            HelloViewBoundaryCli cli = new HelloViewBoundaryCli();
            cli.start();
        } else {
            launch();
        }
    }
}
