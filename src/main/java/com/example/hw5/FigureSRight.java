package com.example.hw5;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Pair;

import java.util.ArrayList;

public class FigureSRight extends FigureType {
    public FigureSRight() {
        body = new Polygon();
        body.getPoints().addAll((double)x2, (double)y2);
        body.getPoints().addAll((double)x4, (double)y2);
        body.getPoints().addAll((double)x4, (double)y3);
        body.getPoints().addAll((double)x3, (double)y3);
        body.getPoints().addAll((double)x3, (double)y4);
        body.getPoints().addAll((double)x1, (double)y4);
        body.getPoints().addAll((double)x1, (double)y3);
        body.getPoints().addAll((double)x2, (double)y3);
        body.setFill(Color.rgb(56, 84, 172));
    }

    @Override
    public ArrayList<Pair<Integer, Integer>> getIndexesOnGrid(StackPane[][] cells, int i, int j) {
        ArrayList<Pair<Integer, Integer>> indexes = new ArrayList<>();
        indexes.add(new Pair<>(i, j));
        if (squareNumber == 0) {
            indexes.add(new Pair<>(i + 1, j));
            indexes.add(new Pair<>(i + 1, j - 1));
            indexes.add(new Pair<>(i + 2, j - 1));
        } else if (squareNumber == 1) {
            indexes.add(new Pair<>(i - 1, j));
            indexes.add(new Pair<>(i, j - 1));
            indexes.add(new Pair<>(i + 1, j - 1));
        } else if (squareNumber == 2) {
            indexes.add(new Pair<>(i - 1, j + 1));
            indexes.add(new Pair<>(i, j + 1));
            indexes.add(new Pair<>(i + 1, j));
        } else {
            indexes.add(new Pair<>(i - 2, j + 1));
            indexes.add(new Pair<>(i - 1, j + 1));
            indexes.add(new Pair<>(i - 1, j));
        }
        return indexes;
    }

    @Override
    public void setPressedSquareNumber(MouseEvent mouseEvent) {
        double cordX = mouseEvent.getSceneX();
        double cordY = mouseEvent.getSceneY();
        if (cordX >= x1 && cordX < x2 && cordY >= y3 && cordY <= y4) {
            squareNumber = 0;
            canMoveFigure = true;
        } else if (cordX >= x2 && cordX <= x3 && cordY >= y3 && cordY <= y4) {
            squareNumber = 1;
            canMoveFigure = true;
        } else if (cordX >= x2 && cordX < x3 && cordY >= y2 && cordY < y3) {
            squareNumber = 2;
            canMoveFigure = true;
        } else if (cordX >= x3 && cordX <= x4 && cordY >= y2 && cordY <= y3) {
            squareNumber = 3;
            canMoveFigure = true;
        } else {
            canMoveFigure = false;
        }
    }
}
