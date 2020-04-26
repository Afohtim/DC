package org.afohtim.lab6a;

import javafx.scene.paint.Color;

public class ColorPalette {
    private static Color[] colors = new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE};
    static private int i = 0;
    static private final int size = 4;

    static Color color(int i) {
        return colors[i];
    }

    static Color nextColor() {
        Color color = colors[i];
        i = (i + 1) % size;
        return color;
    }

    static int size() {
        return size;
    }

}
