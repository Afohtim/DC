package org.lab2a;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Pooh implements Runnable {

    int maxX, maxY;
    public int x, y;

    Random randomGen;

    Pooh(int x, int y) {
        maxX = x;
        maxY = y;

        randomGen = new Random();
        this.x = randomGen.nextInt(maxX);
        this.y = randomGen.nextInt(maxY);
    }

    private int coordinate(int c, int maxC) {
        if(c < 0)
            c = 0;
        if(c >= maxC)
            c = maxC - 1;
        return c;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            x = coordinate(x + randomGen.nextInt(3) - 1, maxX);
            y = coordinate(y + randomGen.nextInt(3) - 1, maxY);
            System.out.println(String.format("Pooh at (%d, %d)", x, y));
            /*
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
             */
        }
    }
}
