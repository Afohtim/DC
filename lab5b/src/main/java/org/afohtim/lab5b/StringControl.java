package org.afohtim.lab5b;

import java.sql.Time;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StringControl {

    private StringCheckBarrier barrier;
    private Vector<RandomSwapString> strings;
    private ExecutorService executor;

    StringControl(int n, int len) {
        barrier = new StringCheckBarrier(n);
        strings = new Vector<>();

        for(int i = 0; i < n; ++ i) {
            strings.add(new RandomSwapString(barrier, len));
        }
        executor = Executors.newFixedThreadPool(n + 1);
    }

    public void run() {
        Thread barrierThread = new Thread(barrier);
        barrierThread.setDaemon(true);
        barrierThread.start();

        for(RandomSwapString string : strings) {
            executor.submit(string);
        }
        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        for(RandomSwapString string : strings) {
            System.out.println(String.format("%d %d %d %d (%d, %d)", string.charCount('a'), string.charCount('b'),
                    string.charCount('c'), string.charCount('d'),
                    string.charCount('a') + string.charCount('b'),
                    string.charCount('c') + string.charCount('d')));
        }

        executor.shutdown();


    }
}
