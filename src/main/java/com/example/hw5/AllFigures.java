package com.example.hw5;

import java.util.ArrayList;

/**
 * Класс фигур.
 * Данный класс реализует возможность создания и хранения фигур.
 */
public class AllFigures {
    // Массив всех фигур.
    private ArrayList<Figure> allFigures = new ArrayList<>();
    // Массив всех фигур в клеточном представлении.
    private ArrayList<int[][]> figures = new ArrayList<>();

    /**
     * Конструктор всех фируг.
     * Они представляются квадратом 3х3,
     * где единицами обозначены активные клетки фигуры.
     */
    public AllFigures() {
        int[][] figure1 = {
                {1, 1, 0},
                {1, 0, 0},
                {1, 0, 0}};
        figures.add(figure1);
        int[][] figure2 = {
                {0, 1, 1},
                {0, 0, 1},
                {0, 0, 1}};
        figures.add(figure2);
        int[][] figure3 = {
                {1, 0, 0},
                {1, 1, 0},
                {0, 1, 0}};
        figures.add(figure3);
        int[][] figure4 = {
                {0, 0, 1},
                {0, 0, 1},
                {1, 1, 1}};
        figures.add(figure4);
        int[][] figure5 = {
                {0, 1, 0},
                {0, 1, 0},
                {1, 1, 1}};
        figures.add(figure5);
        int[][] figure6 = {
                {0, 0, 0},
                {0, 1, 1},
                {0, 1, 0}};
        figures.add(figure6);
        int[][] figure7 = {
                {1, 0, 0},
                {1, 1, 0},
                {1, 0, 0}};
        figures.add(figure7);
        // Переворачиваем фигуры для получения всех их вариаций.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                figures.add(rotate(figures.get(i)));
            }
        }
        int[][] figure8 = {
                {0, 1, 0},
                {0, 1, 0},
                {0, 1, 0}};
        figures.add(figure8);
        figures.add(rotate(figure8));
        int[][] figure9 = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}};
        figures.add(figure9);
        createFigures();
    }

    /**
     * Конструктор с заданием всех фигур.
     * @param allFigures все фигуры
     */
    public AllFigures(ArrayList<Figure> allFigures) {
        this.allFigures = allFigures;
    }

    /**
     * Получение всех фигур.
     * @return массив всех фигур
     */
    public ArrayList<Figure> getAllFigures() {
        return allFigures;
    }

    /**
     * Задание всех фигур.
     * @param allFigures массив фигур
     */
    public void setAllFigures(ArrayList<Figure> allFigures) {
        this.allFigures = allFigures;
    }

    /**
     * Добаление новой фигуры.
     * @param figure добавляемая фигура
     */
    public void addFigure(int[][] figure) {
        allFigures.add(new Figure(10, 10, figure));
    }

    /**
     * Поворот фигуры вокруг ее центра.
     * @param figure фигура
     * @return повернутая фигура
     */
    public int[][] rotate(int[][] figure) {
        int[][] rotate_figure = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rotate_figure[j][-i + 2] = figure[i][j];
            }
        }
        return rotate_figure;
    }

    /**
     * Создание всех фигур из их клеточного представления.
     */
    public void createFigures() {
        for (int i = 0; i < figures.size(); i++) {
            allFigures.add(new Figure(10, 10, figures.get(i)));
        }
    }

    /**
     * Получение фигуры по индексу.
     * @param index индекс фигуры
     * @return фигура
     */
    public Figure getFigure(int index) {
        if (index < allFigures.size()) {
            return allFigures.get(index);
        } else {
            return null;
        }
    }

    /**
     * Получение фигуры (рандомной).
     * @return фигура
     */
    public Figure getFigure() {
        int index = random(0, allFigures.size());
        return getFigure(index);
    }

    /**
     * Задание возможности перемещения для всех фигур.
     */
    public void setMouseTransparentAllFigure() {
        for (Figure figure :
                allFigures) {
            figure.setMouseTransparent(false);
        }
    }

    /**
     * Получение рандомного числа.
     * @param min минимальное значение
     * @param max максимальное значение
     * @return рандомное число в заданных границах
     */
    public int random(int min, int max) {
        return (int)((Math.random() * (max - min) + min));
    }
}
