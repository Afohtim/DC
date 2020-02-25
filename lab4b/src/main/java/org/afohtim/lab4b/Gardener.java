package org.afohtim.lab4b;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Gardener implements Runnable {

    Garden garden;

    Gardener(Garden garden) {
        this.garden = garden;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (!Thread.currentThread().isInterrupted()) {
            int i = random.nextInt(garden.getSize());
            int j = random.nextInt(garden.getSize());
            if(garden.getPlantStatus(i, j) == 0) {
                garden.setPlantStatus(i, j, 1);
            }
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }


}
