module demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.desktop;
    requires jbcrypt;
    requires jdk.jshell;
    requires java.sql;
    requires jakarta.mail;

    exports com.example.mealcalendar;
    opens com.example.mealcalendar to javafx.fxml;
}