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
            changeToNextColor();
        });
        isAlive = false;
    }

    private void changeAliveState(){
        if (isAlive) {
            square.setFill(Color.web("0x2F4F4F"));
            isAlive = false;
        }
        else {
            isAlive = true;
            square.setFill(Color.LIGHTGRAY);
        }
    }

    private void changeAliveColor(Color color){
        square.setFill(color);
    }

    Rectangle getSquare(){
        return square;
    }

    boolean isAlive(){
        return isAlive;
    }

    Color getColor() {
        return (Color)square.getFill();
    }

    void setAlive(boolean isAlive, Color color){
        if (isAlive != this.isAlive) {
            changeAliveState();
        }
        if(isAlive) {
            changeAliveColor(color);
        }


    }

    void changeToNextColor() {
        int ans = 0;
        for(int i = 0; i < ColorPalette.size(); ++i) {
            if(ColorPalette.color(i) == getColor()) {
                ans = i;
            }
        }
        if(isAlive && ans == ColorPalette.size() - 1) {
            setAlive(false, null);
            return;
        }
        if(!isAlive) {
            ans = -1;
        }
        setAlive(true, ColorPalette.color(ans + 1));
    }

}
