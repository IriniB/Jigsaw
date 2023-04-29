package com.example.hw5.Controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {
    public TextField name;
    public static String playerName;
    @FXML
    private Button exitButton;
    @FXML
    public void onStartButtonClick() throws IOException {
        playerName = name.getText();
        if (!GameController.getClientController().connect()) {
            System.out.println("Could not connect to server");
            return;
        }
        if (!GameController.getClientController().checkStart()) {
            System.out.println("Not enough players");
            return;
        }

        // Создание второго окна.
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 900, 600);

        Stage stage = new Stage();
        stage.setTitle("Tetris");
        stage.setScene(scene);
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();

        Stage currentStage = (Stage) exitButton.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void onExitButtonClick() {
        Stage currentStage = (Stage) exitButton.getScene().getWindow();
        currentStage.close();
    }
}
