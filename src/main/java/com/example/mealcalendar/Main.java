package com.example.mealcalendar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;


public class \Main extends Application {


    private static Stage stg;


    public void start(Stage primaryStage) throws IOException {
        primaryStage.setResizable(true); //non ridimensionabile
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280,720);
        primaryStage.setTitle("Q");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch();
    }

}
