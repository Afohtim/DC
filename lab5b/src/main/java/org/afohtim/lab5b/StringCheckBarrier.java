package org.afohtim.lab5b;

import java.util.Comparator;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class StringCheckBarrier implements Runnable {
    private int numberOfThreads;
    private ReentrantLock lock;
    private Semaphore semaphore1;
    private Semaphore semaphore2;
    private Semaphore semaphore3;
    private boolean checkResult;
    private Vector<Integer> abVector;

    StringCheckBarrier(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        this.lock = new ReentrantLock();
        this.abVector = new Vector<>();
        this.semaphore1 = new Semaphore(0);
        this.semaphore2 = new Semaphore(0);
        this.semaphore3 = new Semaphore(0);
        this.checkResult = false;
    }

    boolean stringsAreInBalance(int aCount, int bCount) {
        lock.lock();
        abVector.add(aCount + bCount);
        semaphore2.release();
        lock.unlock();

        try {
            semaphore1.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lock.lock();
        boolean ans = checkResult;
        lock.unlock();

        try {
            semaphore3.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ans;
    }


    private boolean checkStrings() {
        abVector.sort(Comparator.naturalOrder());

        boolean ans = abVector.get(0).equals(abVector.get(2)) ||
                abVector.get(abVector.size() - 1).equals(abVector.get(abVector.size() - 3));
        return ans;

    }


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                semaphore2.acquire(numberOfThreads);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lock.lock();
            checkResult = checkStrings();
            abVector.clear();
            lock.unlock();

            semaphore1.release(numberOfThreads);
            while (semaphore1.availablePermits() > 0)
                Thread.yield();
            semaphore3.release(numberOfThreads);
        }
    }
}
