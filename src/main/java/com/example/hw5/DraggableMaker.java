package com.example.hw5;

import javafx.scene.Node;

public class DraggableMaker {
    private double mouseAnchorX;
    private double mouseAnchorY;

    /**
     Метод делает графический объект перетаскиваемым.
     @param node Объект, который нужно перетаскивать.
     */
    public void makeDraggable(Node node) {
        node.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
            node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
        });
    }
}
