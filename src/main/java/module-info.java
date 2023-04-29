module com.example.hw5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires okhttp3;
    requires com.google.gson;


    opens com.example.hw5 to javafx.fxml;
    exports com.example.hw5;
    exports com.example.hw5.Model;
    opens com.example.hw5.Model to javafx.fxml;
    exports com.example.hw5.Controllers;
    opens com.example.hw5.Controllers to javafx.fxml;
}