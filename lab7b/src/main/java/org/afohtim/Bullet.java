package org.afohtim;

import org.afohtim.assets.*;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.locks.ReentrantLock;

public class Bullet implements Runnable {

    private Coordinate bulletSize;
    private int screenWidth;
    private int screenHeight;
    private DuckHuntGame game;
    private int bulletSpeed = DuckHuntConstants.bulletSpeed;
    private Coordinate current;
    private Coordinate speed;
    private JLabel bulletVisualisation;


    private boolean isAlive;
    private ReentrantLock aliveLock;


    Bullet(Coordinate start, Coordinate target, DuckHuntGame game) {
        this.current = start;

        this.speed = normalizeSpeed(new Coordinate(target.x - start.x, target.y - start.y));

        isAlive = true;

        bulletSize = new Coordinate(DuckHuntConstants.bulletSizeX, DuckHuntConstants.bulletSizeY);
        this.screenWidth = game.getWidth();
        this.screenHeight = game.getHeight();
        this.game = game;
        bulletVisualisation = new JLabel(new ImageIcon(Textures.BULLET));
        bulletVisualisation.setSize(new Dimension(bulletSize.x, bulletSize.y));
        aliveLock = new ReentrantLock();

    }

    private void updateAliveState(Coordinate coordinate) {
        aliveLock.lock();
            if (speed.x > 0 && coordinate.x > screenWidth)
                isAlive = false;
            else if (speed.x < 0 && coordinate.x < -bulletSize.x)
                isAlive = false;
            if (coordinate.y < -bulletSize.y) isAlive = false;
        aliveLock.unlock();
    }

    private int bulletXSpeed(Coordinate direction) {
        if(direction.x == 0)
            return 0;
        if(direction.y == 0)
            return bulletSpeed;
        float c = (float)direction.x/direction.y;
        float speed = bulletSpeed;
        int sign = direction.x > 0 ? 1: -1;
        return sign * (int)(Math.sqrt(1.0 / (1 + c*c)) * speed);
    }

    private int bulletYSpeed(Coordinate direction) {
        if(direction.y == 0)
            return 0;
        float c = (float)direction.x/direction.y;
        float speed = bulletSpeed;
        int sign = direction.y > 0 ? 1: -1;
        return sign * (int)(Math.sqrt(c*c / (1 + c*c)) * speed);
    }

    Coordinate normalizeSpeed(Coordinate direction) {
        double k = (double)(bulletSpeed * bulletSpeed)/(double)(direction.x*direction.x + direction.y * direction.y);
        return new Coordinate((int)((double)direction.x * k), (int)((double)direction.y * k));
    }


    @Override
    public void run() {
        game.add(bulletVisualisation);
        while (!Thread.currentThread().isInterrupted() && isAlive) {
            Coordinate newCoordinate = new Coordinate(current.x + speed.x, current.y + speed.y);

            game.runForeachDuckActionIf(
                    (duck -> duck.isShot(current.x, current.y)),
                    (Duck::kill)
            );
            updateAliveState(newCoordinate);

            current = newCoordinate;
            bulletVisualisation.setLocation(current.x, current.y);

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        game.remove(bulletVisualisation);
        game.repaint();
        game.kill(this);
    }
}
