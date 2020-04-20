package org.afohtim;

import org.afohtim.assets.Textures;

import javax.swing.*;
import java.awt.*;

public class Hunter implements Runnable {
    private JLabel hunterLabel;
    private DuckHuntGame game;

    private int x;
    private int y;

    private static final int sizeX = 150;
    private static final int sizeY = 150;


    public Hunter(DuckHuntGame game) {
        this.game = game;
        x = game.getWidth() / 2;
        y = game.getHeight() - sizeY - 30;

        hunterLabel = new JLabel(new ImageIcon(Textures.HUNTER));
        hunterLabel.setSize(new Dimension(sizeX, sizeY));
        hunterLabel.setLocation(x, y);
        hunterLabel.setVisible(true);
        game.add(hunterLabel);

    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            updatePosition();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        game.remove(hunterLabel);
    }

    private synchronized void updatePosition() {
        hunterLabel.setLocation(x, y);
    }
}
