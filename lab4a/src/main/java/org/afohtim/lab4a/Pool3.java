package org.afohtim.lab4a;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Pool3 implements Runnable {

    DatabaseControl db;
    ExecutorService executor;

    Pool3(DatabaseControl db) {
        this.db = db;
        executor = Executors.newFixedThreadPool(4);
    }

    @Override
    public void run() {
        int i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            int finalI = i;
            if(finalI % 3 == 2) {
                executor.submit(new Writer<>(db.getReadWriteLock(), () -> {
                    return db.deleteRecord(Integer.toString(finalI), Integer.toString(finalI));
                }));
            } else {
                executor.submit(new Writer<>(db.getReadWriteLock(), () -> {
                    return db.addRecord(Integer.toString(finalI), Integer.toString(finalI));
                }));
            }

            System.out.println("e");
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            i++;
        }
    }
}
