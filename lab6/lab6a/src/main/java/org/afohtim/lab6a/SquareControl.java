package org.afohtim.lab6a;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class SquareControl {
    private Rectangle square;
    private boolean isAlive;
    SquareControl(int size){
        square = new Rectangle(size, size);
        square.setStrokeType(StrokeType.INSIDE);
        square.setStroke(Color.BLACK);
        square.setFill(Color.web("0x2F4F4F"));
        square.setOnMouseClicked(event -> {
            changeColor();
        });
        isAlive = false;
    }

    private void changeColor(){
        if (isAlive) {
            square.setFill(Color.web("0x2F4F4F"));
            isAlive = false;
        }
        else {
            isAlive = true;
            square.setFill(Color.LIGHTGRAY);
        }
    }

    Rectangle getSquare(){
        return square;
    }

    boolean isAlive(){
        return isAlive;
    }

    void setAlive(boolean isChanged){
        if (isChanged != this.isAlive)
            changeColor();
    }
}
