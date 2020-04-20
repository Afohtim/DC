package org.afohtim;

import java.awt.*;
import javax.swing.*;

public class GameWindow extends JFrame {
    private static final int gameWidth = 1200;
    private static final int gameHeight = 800;

    GameWindow(String title) {
        super(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        setSize(new Dimension(gameWidth, gameHeight));
        Dimension dim = getToolkit().getScreenSize();
        setLocation(dim.width / 2 - gameWidth / 2, dim.height / 2 - gameHeight / 2);

        DuckHuntGame panel = new DuckHuntGame(this);

        add(panel);
        setVisible(true);
    }


}
