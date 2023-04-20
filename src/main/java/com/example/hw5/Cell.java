package com.example.hw5;

import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Класс ячейки.
 * Данный класс реализует элементраную единицу программы Cell (ячейка),
 * наследуется от класса Label, что позволяет отображать ячейки фигур.
 */
public class Cell extends Label {
    /**
     * Цвет ячейки в активном состоянии.
     */
    public static Color COLOR_ACTIVE_CELL = Color.BLUE;
    /**
     * Цвет ячейки в пассивном состоянии.
     */
    public static final Color COLOR_PASSIVE_CELL = Color.WHITE;
    /**
     * Бэкграунд ячейки в активном состоянии (сразу запослнена).
     */
    public static final Background BACKGROUND_ACTIVE_CELL = new Background(new BackgroundFill(COLOR_ACTIVE_CELL, null, null));
    /**
     * Бэкграунд ячейки в неактивном состоянии.
     */
    public static final Background BACKGROUND_PASSIVE_CELL = null;
    /**
     * Граница ячейки в активном состоянии.
     */
    public static final Border BORDER_ACTIVE_CELL = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0)));
    /**
     * Граница ячейки в неактивном состоянии.
     */
    public static final Border BORDER_PASSIVE_CELL = null;

    // Стандартные размеры ячейки.
    private final int SIZE_X = 75;
    private final int SIZE_Y = 75;
    // Позиции ячейки.
    private int pozX;
    private int pozY;
    // Флаг активности ячейки.
    private boolean isActive = false;

    /**
     * Конструктор ячейки.
     * @param pozX координата по оси Х
     * @param pozY координата по оси У
     */
    public Cell(int pozX, int pozY) {
        this.pozX = pozX;
        this.pozY = pozY;
        this.setMaxSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        this.setPrefSize(SIZE_X, SIZE_Y);
    }

    /**
     * Конструктор ячейки.
     * @param pozX координата по оси Х
     * @param pozY координата по оси У
     * @param isActive флаг активности
     */
    public Cell(int pozX, int pozY, boolean isActive) {
        this.pozX = pozX;
        this.pozY = pozY;
        this.setMaxSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        this.setPrefSize(SIZE_X, SIZE_Y);
        this.isActive = isActive;
        setActive(isActive);
    }

    /**
     * Задание цвета клетки.
     * @param color цвет
     */
    public void setColorActiveCell(Color color) {
        COLOR_ACTIVE_CELL = color;
    }

    /**
     * Задание позиции ячейки.
     * @param pozX координата по оси Х
     * @param pozY координата по оси У
     */
    public void setPoz(int pozX, int pozY) {
        this.pozX = pozX;
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
     * Задание позиции по оси Х.
     * @param pozX координата по оси Х
     */
    public void setPozX(int pozX) {
        this.pozX = pozX;
    }

    /**
     * Получение позиции по оси У.
     * @return позиция по оси У
     */
    public int getPozY() {
        return pozY;
    }

    /**
     * Задание позиции по оси У.
     * @param pozY координата по оси У
     */
    public void setPozY(int pozY) {
        this.pozY = pozY;
    }

    /**
     * Получение активности ячейки.
     * @return true - активна, false - иначе
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Метод активации ячейки.
     * @param active флаг активности.
     */
    public void setActive(boolean active) {
        if (active) {
            isActive = true;
            setBackgroundActive(true);
            setBorderActive(true);
            setOpacity(false);
        } else {
            isActive = false;
            setBackgroundActive(false);
            setBorderActive(false);
        }
    }

    /**
     * Установка активности бэкграунда ячейки.
     * @param active флаг активности
     */
    public void setBackgroundActive(boolean active) {
        if (active) {
            setBackground(BACKGROUND_ACTIVE_CELL);
        } else {
            setBackground(BACKGROUND_PASSIVE_CELL);
        }
    }

    /**
     * Установка активности границы ячейки.
     * @param active флаг активности
     */
    public void setBorderActive(boolean active) {
        if (active) {
            setBorder(BORDER_ACTIVE_CELL);
        } else {
            setBorder(BORDER_PASSIVE_CELL);
        }
    }

    /**
     * Установка прозрачности ячейки.
     * @param isOpacity флаг прозрачности клетки
     */
    public void setOpacity(boolean isOpacity) {
        if (isOpacity) {
            this.setOpacity(0.6);
            setBackgroundActive(true);
        } else {
            this.setOpacity(1.0);
        }
    }
}
