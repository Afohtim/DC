package org.afohtim;

import org.afohtim.assets.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;


public class Duck implements Runnable {
    enum DuckDirection {
        Left, Right
    }

    private static Random random = new Random();
    private Coordinate position;
    private Coordinate speed;

    private int width;
    private DuckDirection direction;
    private Boolean alive = true;
    private ReentrantLock aliveLock;

    private JLabel duckVisualisation;
    private DuckHuntGame game;

    private int turnRate = Math.abs(random.nextInt(2000)) + 1000;
    private long lastTurned;

    public Duck(int newWidth, int newHeight, DuckHuntGame game) {
        width = newWidth;
        int height = newHeight - game.getHeight() * 5 / 12;
        this.game = game;

        direction = getNewDuckType();
        aliveLock = new ReentrantLock();

        if (direction == DuckDirection.Left)
            duckVisualisation = new JLabel(new ImageIcon(Textures.LDUCK));
        else
            duckVisualisation = new JLabel(new ImageIcon(Textures.RDUCK));

        duckVisualisation.setSize(new Dimension(DuckHuntConstants.DuckSizeX, DuckHuntConstants.DuckSizeY));

        speed = new Coordinate(Math.abs(random.nextInt(5)) + 1, -Math.abs(random.nextInt(4)) - 1);
        if (direction == DuckDirection.Left) speed.x = -speed.x;

        position = new Coordinate(Math.abs(random.nextInt(width - 2 * width / 10)) + 2 * width / 10, height);
    }

    public void kill() {
        aliveLock.lock();
            alive = false;
        aliveLock.unlock();
    }

    private void speedUpdater() {
        if (System.currentTimeMillis() - lastTurned >= turnRate) {
            lastTurned = System.currentTimeMillis();
            reverseSpeed();
        }
    }

    private void reverseSpeed() {
        speed.x = -speed.x;
        reverseType();
    }

    private void reverseType() {
        if (direction == DuckDirection.Left) {
            direction = DuckDirection.Right;
            duckVisualisation.setIcon(new ImageIcon(Textures.RDUCK));
        } else {
            direction = DuckDirection.Left;
            duckVisualisation.setIcon(new ImageIcon(Textures.LDUCK));
        }
    }

    private void updateAliveState(Coordinate position) {
        aliveLock.lock();
            if (speed.x > 0 && position.x > width)
                alive = false;
            else if (speed.x < 0 && position.x < -DuckHuntConstants.DuckSizeX)
                alive = false;
            if (position.y < -DuckHuntConstants.DuckSizeY) alive = false;
        aliveLock.unlock();
    }

    public boolean isShot(int posX, int posY) {
        return this.position.x < posX && posX < position.x + DuckHuntConstants.DuckSizeX && position.y < posY && posY < position.y + DuckHuntConstants.DuckSizeY;
    }

    public DuckDirection getNewDuckType() {
        int r = Math.abs(random.nextInt(2));
        if (r == 0)
            return DuckDirection.Left;
        else
            return DuckDirection.Right;
    }

    @Override
    public void run() {
        game.add(duckVisualisation);
        lastTurned = System.currentTimeMillis();
        while (!Thread.currentThread().isInterrupted() && alive) {
            Coordinate newPosition = new Coordinate(position.x + speed.x, position.y + speed.y);
            speedUpdater();
            updateAliveState(newPosition);

            position = newPosition;
            duckVisualisation.setLocation(position.x, position.y);

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        game.remove(duckVisualisation);
        game.repaint();
        game.kill(this);
    }
}
