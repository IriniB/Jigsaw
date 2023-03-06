package com.example.hw5;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Pair;

import java.util.ArrayList;

public class FigureShortLDown extends FigureType {
    public FigureShortLDown() {
        body = new Polygon();
        body.getPoints().addAll((double)x2, (double)y2);
        body.getPoints().addAll((double)x4, (double)y2);
        body.getPoints().addAll((double)x4, (double)y3);
        body.getPoints().addAll((double)x3, (double)y3);
        body.getPoints().addAll((double)x3, (double)y4);
        body.getPoints().addAll((double)x2, (double)y4);
        body.setFill(Color.rgb(56, 84, 172));
    }

    @Override
    public ArrayList<Pair<Integer, Integer>> getIndexesOnGrid(StackPane[][] cells, int i, int j) {
        ArrayList<Pair<Integer, Integer>> indexes = new ArrayList<>();
        indexes.add(new Pair<>(i, j));
        if (squareNumber == 0) {
            indexes.add(new Pair<>(i, j - 1));
            indexes.add(new Pair<>(i + 1, j - 1));
        } else if (squareNumber == 1) {
            indexes.add(new Pair<>(i, j + 1));
            indexes.add(new Pair<>(i + 1, j));
        } else {
            indexes.add(new Pair<>(i - 1, j));
            indexes.add(new Pair<>(i - 1, j + 1));
        }
        return indexes;
    }

    @Override
    public void setPressedSquareNumber(MouseEvent mouseEvent) {
        double cordX = mouseEvent.getSceneX();
        double cordY = mouseEvent.getSceneY();
        if (cordX >= x2 && cordX <= x3 && cordY >= y3 && cordY <= y4) {
            squareNumber = 0;
            canMoveFigure = true;
        } else if (cordX >= x2 && cordX < x3 && cordY >= y2 && cordY < y3) {
            squareNumber = 1;
            canMoveFigure = true;
        } else if (cordX >= x3 && cordX <= x4 && cordY >= y2 && cordY < y3) {
            squareNumber = 2;
            canMoveFigure = true;
        } else {
            canMoveFigure = false;
        }
    }
}
