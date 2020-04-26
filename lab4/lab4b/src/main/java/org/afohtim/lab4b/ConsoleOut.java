package org.afohtim.lab4b;

import java.util.concurrent.TimeUnit;

public class ConsoleOut implements Runnable {
    Garden garden;

    ConsoleOut(Garden garden) {
        this.garden = garden;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted())
        {
            garden.readLock();

            for(int i = 0; i < garden.getSize(); ++i) {
                for(int j = 0; j < garden.getSize(); ++j) {
                    System.out.print(String.format("%d ", garden.getPlantStatus(i, j)));
                }
                System.out.println();
            }
            System.out.println();

            garden.readUnlock();
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

}
