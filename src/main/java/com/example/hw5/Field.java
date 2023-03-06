package com.example.hw5;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;

public class Field {
    AnchorPane anchorPane;
    Figure figure;
    GridPane gridPane;
    StackPane[][] cells;
    int movesCounter;
    public int passedSeconds = 0;
    Button finishGameBtn;
    Label timeLabel;

    public Field(Stage stage) {
        movesCounter = 0;
        FlowPane flowPaneFirst = createFirstPane();
        FlowPane flowPaneSecond = createSecondPane(stage);

        flowPaneFirst.getChildren().add(flowPaneSecond);
        VBox root = new VBox();
        root.getChildren().addAll(flowPaneFirst);
        Scene scene = new Scene(root,577,615);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     Создаем основную часть окна с игровым полем.
     @return Созданную панель со всеми элементами.
     */
    private FlowPane createFirstPane() {
        anchorPane = new AnchorPane();
        anchorPane.setVisible(true);
        anchorPane.setPrefSize(457,616);
        anchorPane.setMaxSize(457,616);
        anchorPane.setMinSize(457,616);
        createCells();
        createField();
        colorField();
        figure = new Figure();
        Random random = new Random();
        figure.createFigure(random.nextInt(0, 31));
        anchorPane.getChildren().add(figure.figureType.body);
        setFieldActions();
        FlowPane flowPane = new FlowPane();
        flowPane.setOrientation(Orientation.HORIZONTAL);
        flowPane.getChildren().add(anchorPane);
        flowPane.setHgap(7);
        return flowPane;
    }

    /**
     Создаем массив панелей для игрового поля.
     */
    private void createCells() {
        cells = new StackPane[9][9];
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                cells[i][j] = new StackPane();
                cells[i][j].setPrefSize(50.5, 50.5);
                cells[i][j].setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0.5))));
            }
        }
    }

    /**
     Создаем игровое поле.
     */
    private void createField() {
        gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                gridPane.add(cells[i][j], i, j);
            }
        }
        anchorPane.getChildren().add(gridPane);
    }

    /**
     Раскрашиваем игровое поле.
     */
    private void colorField() {
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                if (((i >= 3 && i < 6) && (j < 3 || j >= 6)) ||
                        ((j >= 3 && j < 6) && (i < 3 || i >= 6))) {
                    cells[i][j].setStyle("-fx-background-color: #E4E7EF");
                } else {
                    cells[i][j].setStyle("-fx-background-color: #FFFFFF");
                }
            }
        }
    }

    /**
     Задаем события при нажатии на поле.
     */
    private void setFieldActions() {
        anchorPane.setOnMouseReleased(mouseEvent -> {
            if (figure.figureType.canMoveFigure) {
                anchorPane.getChildren().remove(figure.figureType.body);
                if (figure.figureType.trySetFigure(cells, mouseEvent)) {
                    Random random = new Random();
                    figure.createFigure(random.nextInt(0, 31));
                    ++movesCounter;
                } else {
                    figure.createFigure(figure.lastType);
                    colorField();
                }
                anchorPane.getChildren().add(figure.figureType.body);
            }
        });

        anchorPane.setOnMousePressed(mouseEvent -> figure.figureType.setPressedSquareNumber(mouseEvent));

        anchorPane.setOnMouseDragged(mouseEvent -> {
            colorField();
            if (figure.figureType.canMoveFigure) {
                figure.figureType.colorCells(cells, mouseEvent);
            }
        });
    }

    /**
     Создаем часть окна с кнопкой завершения игры и счетчиком секунд.
     @return Созданную панель со всеми элементами.
     */
    private FlowPane createSecondPane(Stage stage) {
        FlowPane flowPaneSecond = new FlowPane();
        flowPaneSecond.setOrientation(Orientation.VERTICAL);
        flowPaneSecond.setStyle("-fx-background-color: #f4f4f4");
        flowPaneSecond.setPrefSize(112,616);
        flowPaneSecond.setMaxSize(112,616);
        flowPaneSecond.setMinSize(112,616);

        createFinishGameBtn(stage);
        flowPaneSecond.getChildren().add(finishGameBtn);

        createTimeLabel();
        flowPaneSecond.getChildren().add(timeLabel);
        return flowPaneSecond;
    }

    /**
     Создаем кнопу для завершения игры.
     */
    private void createFinishGameBtn(Stage stage) {
        finishGameBtn = new Button();
        finishGameBtn.setText("Завершить\nигру");
        finishGameBtn.setPrefSize(100, 70);
        finishGameBtn.setFont(Font.font(15));

        finishGameBtn.setOnAction(actionEvent -> new StartingMenu(stage, passedSeconds,movesCounter));
    }

    /**
     Отображение времени игры в секундах на поле.
     */
    private void createTimeLabel() {
        timeLabel = new Label();
        timeLabel.setPrefSize(100, 70);
        timeLabel.setFont(Font.font(20));

        Timeline fiveSecondsWonder = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            timeLabel.setText(Integer.toString(passedSeconds));
                            ++passedSeconds;
                        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }
}
