module com.example.hw5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hw5 to javafx.fxml;
    exports com.example.hw5;
    exports com.example.hw5.Model;
    opens com.example.hw5.Model to javafx.fxml;
    exports com.example.hw5.View;
    opens com.example.hw5.View to javafx.fxml;
    exports com.example.hw5.Controllers;
    opens com.example.hw5.Controllers to javafx.fxml;
}