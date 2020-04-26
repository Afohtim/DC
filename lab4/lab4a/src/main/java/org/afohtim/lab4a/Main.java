package org.afohtim.lab4a;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Database database = null;
        try {
            database = new Database(new String("db"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        DatabaseControl db = new DatabaseControl(database);
        Thread thread1 = new Thread(new Pool1(db));
        thread1.setDaemon(true);
        Thread thread2 = new Thread(new Pool2(db));
        thread2.setDaemon(true);
        Thread thread3 = new Thread(new Pool3(db));
        thread3.setDaemon(true);

        thread3.start();
        thread1.start();
        thread2.start();

        TimeUnit.SECONDS.sleep(1);
    }
}
