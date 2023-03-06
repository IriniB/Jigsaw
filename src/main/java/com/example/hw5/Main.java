package com.example.hw5;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        new StartingMenu(stage, 0,0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}