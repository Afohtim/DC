package org.afohtim.lab4b;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        int size = 10;
        Garden garden = new Garden(size, null);
        ExecutorService executor = Executors.newFixedThreadPool(10);

        Nature nature = new Nature(garden);
        Gardener gardener = new Gardener(garden);
        ConsoleOut consoleOut = new ConsoleOut(garden);

        executor.submit(nature);
        executor.submit(gardener);
        executor.submit(consoleOut);

        TimeUnit.SECONDS.sleep(1);
    }
}
