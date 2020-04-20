package org.afohtim.lab6a;

import javafx.scene.control.Button;

public class JavaFXButtonFactory {
    static Button createButton(String text, int x, int y, int height, int width, Action action) {
        Button button = createButton(text, x, y, height, width);
        button.setOnAction(event -> action.act());
        return button;
    }

    static Button createButton(String text, int x, int y, int height, int width) {
        Button button = new Button(text);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefSize(width, height);
        return button;
    }
}
