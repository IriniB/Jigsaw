package com.example.hw5;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class Field {
    /**
     * Размер по оси Х.
     */
    public static final int SIZE_CELL_ON_FIELD_X = 66;
    /**
     * Размер по оси У.
     */
    public static final int SIZE_CELL_ON_FIELD_Y = 65;
    /**
     * Размер (количество клеток) по оси Х.
     */
    public static final int SIZE_X = 9;
    /**
     * Размер (количество клеток) по оси У.
     */
    public static final int SIZE_Y = 9;
    // Колонки и строки.
    private final ColumnConstraints COLUMN_CONSTRAINTS = new ColumnConstraints(10, 75, Region.USE_COMPUTED_SIZE, Priority.SOMETIMES, HPos.CENTER, true);
    private final RowConstraints ROW_CONSTRAINTS = new RowConstraints(10, 75, Region.USE_COMPUTED_SIZE, Priority.SOMETIMES, VPos.CENTER, true);
    // Поле.
    private GridPane gridPaneField;
    // Массив всех ячеек.
    private ArrayList<ArrayList<Cell>> allCells = new ArrayList<>();
    private boolean isOpacity = false;

    /**
     * Конструктор поля.
     */
    public Field() {
        gridPaneField = new GridPane();
        for (int i = 0; i < SIZE_X; i++) {
            gridPaneField.getRowConstraints().add(ROW_CONSTRAINTS);
        }
        for (int i = 0; i < SIZE_Y; i++) {
            gridPaneField.getColumnConstraints().add(COLUMN_CONSTRAINTS);
        }
        createField();
    }

    /**
     * Конструктор поля.
     * @param gridPaneField панель поля
     */
    public Field(GridPane gridPaneField) {
        this.gridPaneField = gridPaneField;
        createField();
    }

    /**
     * Получение панели.
     * @return панель
     */
    public GridPane getGridPaneField() {
        return gridPaneField;
    }

    /**
     * Задание панели.
     * @param gridPaneField панель
     */
    public void setGridPaneField(GridPane gridPaneField) {
        this.gridPaneField = gridPaneField;
    }

    /**
     * Получение всех клеток.
     * @return все клетки
     */
    public ArrayList<ArrayList<Cell>> getAllCells() {
        return allCells;
    }

    /**
     * Получение клетки по позиции.
     * @param pozX координата по оси Х
     * @param pozY координата по оси У
     * @return клетка на заданных координатах
     */
    public Cell getCell(int pozX, int pozY) {
        return allCells.get(pozY).get(pozX);
    }

    /**
     * Получение прозрачности.
     * @return true - прозрачно, false - иначе
     */
    public boolean isOpacity() {
        return isOpacity;
    }

    /**
     * Задание флага прозрачности.
     * @param opacity флаг прозрачности
     */
    public void setOpacity(boolean opacity) {
        isOpacity = opacity;
    }

    /**
     * Задание всех ячеек.
     * @param allCells пассив ячеек
     */
    public void setAllCells(ArrayList<ArrayList<Cell>> allCells) {
        this.allCells = allCells;
    }

    /**
     * Метод проверяющий активность указанной ячейки поля.
     * @param pozX координата по оси Х
     * @param pozY координата по оси У
     * @return true - активна, false - иначе
     */
    public boolean isCellActive(int pozX, int pozY) {
        Cell cell = allCells.get(pozY).get(pozX);
        return cell.isActive();
    }

    /**
     * Метод создания поля.
     */
    public void createField() {
        for (int i = 0; i < SIZE_X; i++) {
            ArrayList<Cell> list = new ArrayList<>();
            for (int j = 0; j < SIZE_Y; j++) {
                Cell cell = new Cell(j, i);
                cell.setActive(false);
                list.add(cell);
                gridPaneField.add(cell, j, i);
            }
            allCells.add(list);
        }
    }

    /**
     * Метод возвращения ячеек в исходное состояние до взаимодействия.
     */
    public void allDelOpacity() {
        if (isOpacity)
            for (int i = 0; i < allCells.size(); i++) {
                for (int j = 0; j < allCells.get(i).size(); j++) {
                    Cell cell = allCells.get(i).get(j);
                    if (!cell.isActive()) {
                        cell.setOpacity(false);
                        cell.setBackgroundActive(false);
                    }
                }
            }
    }

    /**
     * Метод устанавливающий активность всех ячеек.
     * @param active флаг активности
     */
    public void allActive(boolean active) {
        for (int i = 0; i < allCells.size(); i++) {
            for (int j = 0; j < allCells.get(i).size(); j++) {
                allCells.get(i).get(j).setActive(active);
            }
        }
    }

    /**
     * Установка всех ячеек в неактивное состояние.
     */
    public void delActiveCell() {
        for (int i = 1; i < gridPaneField.getChildren().size(); i++) {
            Cell cell = (Cell) gridPaneField.getChildren().get(i);
            cell.setBackground(Cell.BACKGROUND_PASSIVE_CELL);
            cell.setBorder(Cell.BORDER_PASSIVE_CELL);
        }
    }
}
