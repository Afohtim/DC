package org.afohtim;

import org.afohtim.assets.Textures;

import javax.swing.*;
import java.awt.*;

public class Hunter implements Runnable {


    private JLabel hunterLabel;
    private DuckHuntGame game;

    Coordinate currentPosition;

    private static final int sizeX = 150;
    private static final int sizeY = 150;

    private int dx = 20;

    public Hunter(DuckHuntGame game) {
        this.game = game;
        currentPosition = new Coordinate(game.getWidth() / 2, game.getHeight() - sizeY - 30);
        hunterLabel = new JLabel(new ImageIcon(Textures.HUNTER));
        hunterLabel.setSize(new Dimension(sizeX, sizeY));
        hunterLabel.setLocation(currentPosition.x, currentPosition.y);
        hunterLabel.setVisible(true);
        game.add(hunterLabel);

    }

    private synchronized void updatePosition() {
        hunterLabel.setLocation(currentPosition.x, currentPosition.y);
    }

    synchronized Coordinate getPosition() {
        return currentPosition;
    }

    private void updateDX() {
        if(currentPosition.x < 20 || currentPosition.x > game.getWidth() - 200) dx *= -1;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            currentPosition = new Coordinate(currentPosition.x + dx, currentPosition.y);
            updatePosition();
            updateDX();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        game.remove(hunterLabel);
    }



}
