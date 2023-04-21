package com.example.hw5.Controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {
    @FXML
    private Button exitButton;
    @FXML
    public void onStartButtonClick() throws IOException {
        // Создание второго окна.
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 900, 600);

        Stage stage = new Stage();
        stage.setTitle("Tetris");
        stage.setScene(scene);
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();
    }

    @FXML
    public void onExitButtonClick() {
        Stage currentStage = (Stage) exitButton.getScene().getWindow();
        currentStage.close();
    }
}
