package com.example.hw5;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class StartingMenu {
    AnchorPane flowPane;

    public StartingMenu(Stage stage, int time, int movesNumber) {
        createWindow(stage, time, movesNumber);
        VBox rootMenu = new VBox();
        rootMenu.getChildren().addAll(flowPane);
        Scene scene = new Scene(rootMenu,200,280);
        stage.setScene(scene);
        stage.show();
    }

    /**
     Заполнение панели окна элементами.
     */
    private void createWindow(Stage stage, int time, int movesNumber) {
        flowPane = new AnchorPane();
        flowPane.setVisible(true);

        Button startNewGameBtn = new Button();
        createStartNewGameBtn(startNewGameBtn);
        TextField textField = new TextField();
        Button exitBtn = new Button();
        createExitBtn(exitBtn);
        Label lastMovesNumberLabel = new Label();
        createLastMovesNumberLabel(lastMovesNumberLabel, movesNumber);
        Label timeLabel = new Label();
        createTimeLabel(timeLabel, time);

        startNewGameBtn.setOnAction(actionEvent -> {
            new Field(stage);
        });

        exitBtn.setOnAction(actionEvent -> {
            Stage currentStage = (Stage) exitBtn.getScene().getWindow();
            currentStage.close();
        });
        flowPane.getChildren().addAll(startNewGameBtn, exitBtn, lastMovesNumberLabel,timeLabel);
    }

    /**
     Создание кнопки начала игры.
     */
    private void createStartNewGameBtn(Button startNewGameBtn) {
        startNewGameBtn.setText("Начать новую игру");
        startNewGameBtn.setPrefSize(190, 70);
        startNewGameBtn.setFont(Font.font(18));
        AnchorPane.setLeftAnchor(startNewGameBtn, 5.0);
        AnchorPane.setRightAnchor(startNewGameBtn, 5.0);
        AnchorPane.setTopAnchor(startNewGameBtn, 5.0);
    }

    /**
     Создание кнопки выхода из игры.
     */
    private void createExitBtn(Button exitBtn) {
        exitBtn.setText("Выйти");
        exitBtn.setPrefSize(190, 70);
        exitBtn.setFont(Font.font(20));
        AnchorPane.setLeftAnchor(exitBtn, 5.0);
        AnchorPane.setRightAnchor(exitBtn, 5.0);
        AnchorPane.setTopAnchor(exitBtn, 80.0);
    }

    /**
     Создание лейбла с отображением последнего количества ходов игрока.
     */
    private void createLastMovesNumberLabel(Label lastMovesNumberLabel, int movesNumber) {
        lastMovesNumberLabel.setText("Последнее количество\nходов: " + movesNumber);
        lastMovesNumberLabel.setPrefSize(190, 70);
        lastMovesNumberLabel.setFont(Font.font(15));
        AnchorPane.setLeftAnchor(lastMovesNumberLabel, 5.0);
        AnchorPane.setRightAnchor(lastMovesNumberLabel, 5.0);
        AnchorPane.setTopAnchor(lastMovesNumberLabel, (double)150);
    }

    /**
     Создание лейбла с отображением последнего времени игры.
     */
    private void createTimeLabel(Label timeLabel, int time) {
        timeLabel.setText("Последнее время\n(сек): " + time);
        timeLabel.setPrefSize(190, 70);
        timeLabel.setFont(Font.font(15));
        AnchorPane.setLeftAnchor(timeLabel, 5.0);
        AnchorPane.setRightAnchor(timeLabel, 5.0);
        AnchorPane.setTopAnchor(timeLabel, (double)200);
    }

}
