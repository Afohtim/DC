package org.afohtim.lab4a;

import java.util.concurrent.*;

public class Pool1 implements Runnable {

    DatabaseControl db;
    ExecutorService executor;

    Pool1(DatabaseControl db) {
        this.db = db;
        executor = Executors.newFixedThreadPool(4);
    }

    @Override
    public void run() {
        int i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            int finalI = i;
            Future<?> ans = executor.submit(new Reader<>(db.getReadWriteLock(), () -> {
                return db.findPhoneByName(Integer.toString(finalI));
            }));

            while(!ans.isDone()) {
                Thread.yield();
            }

            try {
                System.out.println(String.format("phone number of %s: %s", Integer.toString(i), ans.get()));
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }


            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            i++;
        }
    }
}
