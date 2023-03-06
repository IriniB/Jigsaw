package com.example.hw5;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Pair;

import java.util.ArrayList;

public class FigurePoint extends FigureType {
    public FigurePoint() {
        body = new Polygon();
        body.getPoints().addAll((double)x2, (double)y2);
        body.getPoints().addAll((double)x3, (double)y2);
        body.getPoints().addAll((double)x3, (double)y3);
        body.getPoints().addAll((double)x2, (double)y3);
        body.setFill(Color.rgb(56, 84, 172));
    }

    @Override
    public ArrayList<Pair<Integer, Integer>> getIndexesOnGrid(StackPane[][] cells, int i, int j) {
        ArrayList<Pair<Integer, Integer>> indexes = new ArrayList<>();
        indexes.add(new Pair<>(i, j));
        return indexes;
    }

    @Override
    public void setPressedSquareNumber(MouseEvent mouseEvent) {
        double cordX = mouseEvent.getSceneX();
        double cordY = mouseEvent.getSceneY();
        if (cordX >= x2 && cordX < x3 && cordY >= y2 && cordY <= y3) {
            squareNumber = 0;
            canMoveFigure = true;
        } else {
            canMoveFigure = false;
        }
    }
}
