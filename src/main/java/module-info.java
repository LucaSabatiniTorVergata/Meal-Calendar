module demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires jbcrypt;
    requires jdk.jshell;
    requires jakarta.mail;
    requires com.google.protobuf;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires com.google.gson;


    exports com.example.mealcalendar;
    opens com.example.mealcalendar to javafx.fxml;

    exports com.example.mealcalendar.register_login to javafx.fxml;
    opens com.example.mealcalendar.register_login to javafx.fxml;

    exports com.example.mealcalendar.view_controller;
    opens com.example.mealcalendar.view_controller to javafx.fxml;


    exports com.example.mealcalendar.bean;
    opens com.example.mealcalendar.bean to com.google.gson;

    exports com.example.mealcalendar.model;
    opens com.example.mealcalendar.model to com.google.gson;
    exports com.example.mealcalendar.patternobserver;
    opens com.example.mealcalendar.patternobserver to javafx.fxml;


}
