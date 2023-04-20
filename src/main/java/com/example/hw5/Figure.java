package com.example.hw5;

import javafx.geometry.HPos;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Figure extends GridPane {
    /**
     * Начальное положение по оси Х.
     */
    public static final int START_LAYOUT_X = 650;
    /**
     * Начальное положение по оси У.
     */
    public static final int START_LAYOUT_Y = 76;
    /**
     * Размеры фигуры.
     */
    public static final int SIZE_X = 195;
    public static final int SIZE_Y = 195;
    // Колонки и строки фигуры.
    private final ColumnConstraints COLUMN_CONSTRAINTS = new ColumnConstraints(10, 75, Region.USE_COMPUTED_SIZE, Priority.SOMETIMES, HPos.CENTER, true);
    private final RowConstraints ROW_CONSTRAINTS = new RowConstraints(10, 75, Region.USE_COMPUTED_SIZE, Priority.SOMETIMES, VPos.CENTER, true);
    // Координаты фигуры.
    private int pozX;
    private int pozY;
    // Массив активных клеток.
    private ArrayList<Cell> allActiveCells = new ArrayList<>();
    // Массив неактивных клеток.
    private ArrayList<Cell> allPassiveCells = new ArrayList<>();
    private Random rand = new Random();


    /**
     * Конструктор фигуры.
     * @param pozX координата по оси Х
     * @param pozY координата по оси У
     */
    public Figure(int pozX, int pozY) {
        this.pozX = pozX;
        this.pozY = pozY;
        for (int i = 0; i < 3; i++) {
            this.getColumnConstraints().add(COLUMN_CONSTRAINTS);
            this.getRowConstraints().add(ROW_CONSTRAINTS);
        }
        this.setPrefSize(SIZE_X, SIZE_Y);
        this.setStyle("");
        setStartLayout();
    }

    /**
     * Конструктор фигуры.
     * @param pozX координата по оси Х
     * @param pozY координата по оси У
     * @param figure фигура
     */
    public Figure(int pozX, int pozY, int[][] figure) {
        this.pozX = pozX;
        this.pozY = pozY;
        for (int i = 0; i < 3; i++) {
            this.getColumnConstraints().add(COLUMN_CONSTRAINTS);
            this.getRowConstraints().add(ROW_CONSTRAINTS);
        }
        this.setPrefSize(SIZE_X, SIZE_Y);
        this.setStyle("");
        setStartLayout();
        createFigure(figure);
    }

    /**
     * Задание позиции по оси Х.
     * @param pozX координата по оси Х
     */
    public void setPozX(int pozX) {
        this.pozX = pozX;
    }

    /**
     * Задание позиции по оси У.
     * @param pozY координата по оси У
     */
    public void setPozY(int pozY) {
        this.pozY = pozY;
    }

    /**
     * Получение позиции по оси Х.
     * @return позиция по оси Х
     */
    public int getPozX() {
        return pozX;
    }

    /**
     * Получение позиции по оси У.
     * @return позиция по оси У
     */
    public int getPozY() {
        return pozY;
    }

    /**
     * Возврат активных клеток.
     * @return массив активных клеток.
     */
    public ArrayList<Cell> getAllActiveCells() {
        return allActiveCells;
    }

    /**
     * Возврат неактивных клеток.
     * @return массив неактивных клеток.
     */
    public ArrayList<Cell> getAllPassiveCells() {
        return allPassiveCells;
    }

    /**
     * Метод создания фигуры.
     * @param figure массив фигур
     */
    public void createFigure(int[][] figure) {
        Color color = getColor();
        if (figure.length < 9) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Cell cell = new Cell(j, i);
                    if (figure[i][j] == 1) {
                        cell.setActive(true);
                        cell.setColorActiveCell(color);
                        allActiveCells.add(cell);
                    } else {
                        cell.setActive(false);
                        allPassiveCells.add(cell);
                    }
                    this.add(cell, j, i);
                }
            }
        }
    }

    /**
     * Получение рандомного цвета.
     * @return цвет
     */
    private Color getColor() {
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return Color.color(r, g, b);
    }

    /**
     * Метод задающий начальное положение.
     */
    public void setStartLayout() {
        this.setLayoutX(START_LAYOUT_X);
        this.setLayoutY(START_LAYOUT_Y);
    }
}
