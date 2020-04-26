package org.afohtim;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameControl extends Thread {
    private static final int timeBetweenDucks = 1000;
    private DuckHuntGame game;
    private ExecutorService duckExecutor = Executors.newFixedThreadPool(DuckHuntConstants.maxGameDucks);
    private ExecutorService hunterExecutor = Executors.newFixedThreadPool(1);

    GameControl(DuckHuntGame game) {
        this.game = game;
    }

    @Override
    public void run() {
        hunterExecutor.execute(game.hunter());

        while (!Thread.currentThread().isInterrupted()) {
            if (game.ducksNeeded()) {
                Duck duck = new Duck(game.getWidth(), game.getHeight(), game);
                game.addDuck(duck);
                duckExecutor.submit(duck);
            }
            try {
                sleep(timeBetweenDucks);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}