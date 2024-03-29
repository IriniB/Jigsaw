package com.example.hw5.Controllers;

import com.example.hw5.Model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;

/**
 * Класс контроллера.
 * Данный класс реализует взаимодействие пользователя с интерфейсом.
 */
public class GameController {
    // Переменные и объекты, находящиеся на окне игры.
    @FXML
    public Label labelTime, labelMessage, labelBackground;
    @FXML
    public GridPane gridPaneField;
    @FXML
    public Pane paneFigures;
    @FXML
    public Button buttonFinish, buttonNewGame, buttonExit, leaderboardBtn;
    @FXML
    public AnchorPane anchorPaneField;
    @FXML
    public HBox hBoxOnButton;
    /**
     * Поле игры.
     */
    public static Field fieldTetris;
    /**
     * Все фигуры.
     */
    public static AllFigures allFiguresTetris = new AllFigures();
    /**
     * Текущая фигура.
     */
    public static Figure figureTetris = new Figure(10, 10);
    /**
     * Счетчик ходов.
     */
    public static long step = 0;
    /**
     * Начальное время.
     */
    public Date startTime;
    /**
     * Таймер.
     */
    public Timer timerTimeLabel;
    /**
     * Лейбл для таймера.
     */
    public TimeLabel timeLabel;

    private static final JigsawClientController clientController = new JigsawClientController();

    public static JigsawClientController getClientController() {
        return clientController;
    }

    @FXML
    private TableView<LiderboardRow> tableUsers;

    @FXML
    private TableColumn<LiderboardRow, String> nameColumn;

    @FXML
    private TableColumn<LiderboardRow, String> movesColumn;

    @FXML
    private TableColumn<LiderboardRow, String> secondsColumn;

    /**
     * Инициализация игры.
     */
    public void initialize() {
        startTime = new Date();
        setDragAndDrop(allFiguresTetris);
        //figureTetris = allFiguresTetris.getFigure();
        figureTetris = clientController.getNextFigure();
        setDragAndDrop(figureTetris);
        anchorPaneField.getChildren().add(figureTetris);
        fieldTetris = new Field(gridPaneField);
        timerTimeLabel = new Timer();
        timeLabel = new TimeLabel(startTime, labelTime);
        timerTimeLabel.schedule(timeLabel, 1000, 1000);
    }

    /**
     * Метод реализации нового хода.
     */
    public void nextMove() {
        anchorPaneField.getChildren().remove(figureTetris);
        figureTetris.setVisible(true);
        figureTetris.setStartLayout();

        figureTetris = clientController.getNextFigure();
        if (figureTetris == null) {
            onActionFinish();
        }
        setDragAndDrop(figureTetris);

        anchorPaneField.getChildren().add(figureTetris);
    }

    /**
     * Получение поля.
     * @return поле
     */
    public static Field getFieldTetris() {
        return fieldTetris;
    }

    /**
     * Задание Drag&Drop для всех фигур.
     * @param allFigures фигуры
     */
    public void setDragAndDrop(AllFigures allFigures) {
        for (Figure figure :
                allFigures.getAllFigures()) {
            setDragAndDrop(figure);
        }
    }

    /**
     * Задание Drag&Drop для фигуры.
     * @param figure фигура
     */
    public void setDragAndDrop(Figure figure) {
        for (Cell cell :
                figure.getAllActiveCells()) {
            setDragAndDrop(cell);
        }
        for (Cell cell :
                figure.getAllPassiveCells()) {
            setDragAndDrop(cell);
        }
    }

    /**
     * Задание Drag&Drop для ячейки.
     * @param cell ячейка
     */
    public void setDragAndDrop(Cell cell) {
        if (cell.isActive()) {
            cell.addEventHandler(MouseEvent.DRAG_DETECTED, mouseEvent -> {
                Figure figure = (Figure) cell.getParent();
                Field field = fieldTetris;
                double x = mouseEvent.getSceneX() - figure.getLayoutX();
                double y = mouseEvent.getSceneY() - figure.getLayoutY();
                cell.setOnMouseDragged(mouseEvent1 -> {
                    mouseDrag(mouseEvent1, x, y, figure, field);
                });
                cell.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseEvent2 -> {
                    mouseDrop(mouseEvent2, figure, field);
                });
            });
        } else {
            cell.setMouseTransparent(true);
        }
    }

    // Метод реализующий перетаскивание.
    private void mouseDrag(MouseEvent mouseEvent, double x, double y, Figure figure, Field field) {
        figure.setLayoutX(mouseEvent.getSceneX() - x);
        figure.setLayoutY(mouseEvent.getSceneY() - y);
        Point2D point2D = figure.getPoz();
        if (figure.isPozAllCell((int) point2D.getX(), (int) point2D.getY())) {
            if (!(point2D.getX() == figure.getPozX() && point2D.getY() == figure.getPozY())) {
                // Если можно поставить фигуру, то показываем возможное расположение прозрачными ячейками.
                field.allDelOpacity();
                field.setOpacity(true);
                for (Cell cell :
                        figure.getAllActiveCells()) {
                    Cell addOpacityCell = field.getCell(
                            (int) point2D.getX() + cell.getPozX(), (int) point2D.getY() + cell.getPozY());
                    addOpacityCell.setOpacity(true);
                    field.setOpacity(true);
                }
            }
        } else {
            // Если нельзя заполнить все прозрачные ячейки удаляются.
            field.allDelOpacity();
            field.setOpacity(true);
        }
    }

    // Метод реализующий отпускание кнопки мыши.
    private void mouseDrop(MouseEvent mouseEvent, Figure figure, Field field) {
        Point2D point2D = figure.getPoz();
        // Если можно поставить фигуру, то помещаем ее на поле.
        if (figure.isPozAllCell((int)point2D.getX(), (int)point2D.getY())) {
            figure.setVisible(false);
            for (Cell cell :
                    figure.getAllActiveCells()) {
                Cell addCell = field.getCell(
                        (int)point2D.getX() + cell.getPozX(), (int)point2D.getY() + cell.getPozY());
                addCell.setMouseTransparent(true);
                addCell.COLOR_ACTIVE_CELL = cell.COLOR_ACTIVE_CELL;
                addCell.BACKGROUND_ACTIVE_CELL = new Background(new BackgroundFill(cell.COLOR_ACTIVE_CELL, null, null));
                addCell.setActive(true);
            }
            step++;
            nextMove();
        } else {
            // Иначе возвращаем в начальное положение.
            figure.setStartLayout();
        }
    }

    /**
     * Метод завершения игры.
     */
    public void onActionFinish() {
        // Останавливаем таймер и собираем строку с итогами игры.
        timerTimeLabel.cancel();
        if (figureTetris != null) {
            figureTetris.setMouseTransparent(true);
        }
        String s = "Время игры:\n";
        s += labelTime.getText() + "\n";
        s += "Количество ходов:\n";
        s += step;
        labelMessage.setText(s);
        labelBackground.setOpacity(0.85);
        labelBackground.setVisible(true);
        labelMessage.setVisible(true);
        buttonFinish.setVisible(false);
        hBoxOnButton.setVisible(true);
        leaderboardBtn.setVisible(true);
        tableUsers.setVisible(false);

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        TimeZone gmt = TimeZone.getTimeZone("GMT");
        sdf.setTimeZone(gmt);
        Date gameTime = new Date();
        try {
            gameTime = sdf.parse(labelTime.getText());
        } catch (ParseException exception){
            System.out.println(exception.toString());
        }
        long seconds = gameTime.getTime() / 1000;
        DbController dbController = new DbController();
        dbController.insertResult(StartController.playerName, step, seconds);

        clientController.endGame(new LiderboardRow(StartController.playerName, step, seconds));
    }

    /**
     * Метод перезапуска игры.
     */
    @FXML
    public void onNewActionGame() {
        if (!clientController.connect()) {
            return;
        }
        if (!clientController.checkStart()) {
            return;
        }

        // Установка переменных на начальные значения.
        anchorPaneField.getChildren().remove(figureTetris);
        figureTetris = clientController.getNextFigure();
        if (figureTetris == null) {
            return;
        }

        gridPaneField.setOpacity(1);
        labelBackground.setVisible(false);
        labelMessage.setVisible(false);
        hBoxOnButton.setVisible(false);
        leaderboardBtn.setVisible(false);
        buttonFinish.setVisible(true);
        tableUsers.setVisible(false);

        labelTime.setText("00:00:00");
        startTime = new Date();
        step = 0;

        allFiguresTetris = new AllFigures();
        setDragAndDrop(allFiguresTetris);

        setDragAndDrop(figureTetris);

        anchorPaneField.getChildren().add(figureTetris);
        fieldTetris = new Field(gridPaneField);
        fieldTetris.delActiveCell();
        fieldTetris.allActive(false);
        timerTimeLabel = new Timer();
        timeLabel = new TimeLabel(startTime, labelTime);
        timerTimeLabel.schedule(timeLabel, 1000, 1000);
    }

    /**
     * Метод выхода из игры.
     */
    @FXML
    public void onActionExit() {
        Platform.exit();
        System.exit(0);
    }


    public void onLeaderboardBtnClick() {
        DbController dbController = new DbController();
        ArrayList<LiderboardRow> data = dbController.getLeaderboard();
        ObservableList<LiderboardRow> usersData = FXCollections.observableArrayList();
        usersData.addAll(data);

        nameColumn.setCellValueFactory(new PropertyValueFactory<LiderboardRow, String>("name"));
        movesColumn.setCellValueFactory(new PropertyValueFactory<LiderboardRow, String>("moves"));
        secondsColumn.setCellValueFactory(new PropertyValueFactory<LiderboardRow, String>("seconds"));

        tableUsers.setItems(usersData);
        System.out.println(usersData);
        tableUsers.setVisible(true);
    }
}
