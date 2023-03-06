package com.example.hw5;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.ArrayList;

public class FigureType {
    // Координаты, по которым изначально строятся все фигуры.
    protected static int x1 = 150;
    protected static int x2 = 200;
    protected static int x3 = 250;
    protected static int x4 = 300;
    protected static int y1 = 460;
    protected static int y2 = 510;
    protected static int y3 = 560;
    protected static int y4 = 610;

    protected Polygon body;
    // Показывает, нажал ли пользователь в области границы фигуры.
    protected boolean canMoveFigure = false;
    protected int squareNumber = 0;
    // Прямоугольник, который помещается в ячейку игрового поля при установке фигуры.
    private Rectangle rectangle;

    /**
     Метод задает внешний вид прямоугольника для отображения фигур на поле.
     */
    private void createNewRectangle(Color color) {
        rectangle = new Rectangle();
        rectangle.setHeight(49);
        rectangle.setWidth(49);
        rectangle.setFill(color);
        rectangle.setStrokeWidth(0.5);
    }

    /**
     Метод перебирает все ячейки, в которые предположительно должна быть установлена фигура, и
     определяет, можно ли ее в них установить.
     @param cells Массив панелей всех ячеек поля.
     @param mouseEvent Событие при отпускании пользователем правой кнопки мыши над полем.
     @return True, если фигура может быть установлена, false - иначе.
     */
    public boolean trySetFigure(StackPane[][] cells, MouseEvent mouseEvent) {
        int i = (int)mouseEvent.getSceneX() / 50;
        int j = (int)mouseEvent.getSceneY() / 50;
        ArrayList<Pair<Integer, Integer>> indexes = getIndexesOnGrid(cells, i, j);
        if (canSetFigure(cells, indexes)) {
            setFigureInCells(cells, indexes);
            return true;
        } else {
            return false;
        }
    }

    /**
     Метод получает все координаты ячеек, в которые должна быть установлена фигура
     в зависимости от ее типа.
     @param cells Массив панелей всех ячеек поля.
     @param i Индекс ячейки поля, над которой пользователь отпустил кнопку мыши, по оси x.
     @param j Индекс ячейки поля, над которой пользователь отпустил кнопку мыши, по оси y.
     @return Массив с индексами ячеек, в которые пользователь хочет установить фигуру.
     */
    protected ArrayList<Pair<Integer, Integer>> getIndexesOnGrid(StackPane[][] cells, int i, int j) {
        return null;
    }

    /**
     Метод определяет номер квадрата фигуры, по которому пользователь нажал для перетаскивания фигуры.
     Также устанавливает значение поля canMoveFigure.
     @param mouseEvent Событие при отпускании пользователем правой кнопки мыши над полем.
     */
    protected void setPressedSquareNumber(MouseEvent mouseEvent) {  }

    /**
     Метод устанавливает фигуру на поле, т.е. ставит прямоугольники во все ячейки, в которых
     должна находиться фигура.
     @param cells Массив панелей всех ячеек поля.
     @param indexes Массив с индексами ячеек, в которые надо установить фигуру.
     */
    protected void setFigureInCells(StackPane[][] cells, ArrayList<Pair<Integer, Integer>> indexes) {
        for (var index : indexes) {
            createNewRectangle(Color.rgb(56, 84, 172));
            cells[index.getKey()][index.getValue()].getChildren().add(rectangle);
        }
    }

    /**
     Метод проверяет, может ли быть установлена фигура в ячейки.
     @param cells Массив панелей всех ячеек поля.
     @param indexes Массив с индексами ячеек, в которые надо установить фигуру.
     */
    protected boolean canSetFigure(StackPane[][] cells, ArrayList<Pair<Integer, Integer>> indexes) {
        for (var index : indexes) {
            if (index.getKey() > 8 || index.getKey() < 0 ||
                    index.getValue() > 8 || index.getValue() < 0 ||
                    !cells[index.getKey()][index.getValue()].getChildren().isEmpty()) {
                return false;
            }
        }
        return true;
    }


    public void colorCells(StackPane[][] cells, MouseEvent mouseEvent) {
        int i = (int)mouseEvent.getSceneX() / 50;
        int j = (int)mouseEvent.getSceneY() / 50;
        ArrayList<Pair<Integer, Integer>> indexes = getIndexesOnGrid(cells, i, j);

        if (canSetFigure(cells, indexes)) {
            for (var index : indexes) {
                if (index.getKey() < 9 && index.getKey() >= 0 &&
                        index.getValue() < 9 && index.getValue() >= 0 &&
                        cells[index.getKey()][index.getValue()].getChildren().isEmpty()) {
                    cells[index.getKey()][index.getValue()].setStyle("-fx-background-color: #9ACD32");
                }
            }
        } else {
            for (var index : indexes) {
                if (index.getKey() < 9 && index.getKey() >= 0 &&
                        index.getValue() < 9 && index.getValue() >= 0 &&
                        cells[index.getKey()][index.getValue()].getChildren().isEmpty()) {
                    cells[index.getKey()][index.getValue()].setStyle("-fx-background-color: #A52A2A");
                }
            }
        }
    }
}
