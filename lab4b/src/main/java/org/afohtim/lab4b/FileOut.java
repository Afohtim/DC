package org.afohtim.lab4b;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class FileOut implements Runnable {
    Garden garden;
    File file;
    BufferedWriter pw;

    FileOut(Garden garden) {
        this.garden = garden;
        this.file = new File("garden.txt");
        this.pw = null;
        try {
            new FileWriter(file).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted())
        {
            try {
                garden.readLock();
                pw = new BufferedWriter(new FileWriter(file, true));

                for(int i = 0; i < garden.getSize(); ++i) {
                    for(int j = 0; j < garden.getSize(); ++j) {
                        pw.write(String.format("%d ", garden.getPlantStatus(i, j)));

                    }
                    pw.write("\n");
                }
                    pw.write("\n");

                pw.close();
                garden.readUnlock();
            } catch (IOException e) {
                e.printStackTrace();

            }

            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }
}
