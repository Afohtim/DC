package com.lab3a;

import java.util.concurrent.Semaphore;

public class Pot {
    private Semaphore semaphore;
    private int n;
    private int maxN;
    private boolean fullConfirmed;

    Pot(int n) {
        semaphore = new Semaphore(1);
        this.maxN = n;
        this.n = 0;
    }

    boolean tryPut(){
        if(n < maxN)
        {
            if(semaphore.tryAcquire()) {
                n++;
                semaphore.release();
            }
            return true;
        }
        return false;
    }

    boolean put() {
        boolean putSuccess = false;
        while(!putSuccess) {
            putSuccess = tryPut();
        }

        return isFull();
    }

    boolean tryEatAll() {
        if(n == maxN)
        {
            if(semaphore.tryAcquire()) {
                n = 0;
                fullConfirmed = false;
                semaphore.release();
                return true;
            }
        }
        return false;
    }

    void eatAll() {
        boolean eatSuccess = false;
        while (!eatSuccess)
        {
            eatSuccess = tryEatAll();
        }
    }

    boolean isFull() {
        if(fullConfirmed)
            return false;
        while(semaphore.tryAcquire());
        boolean res = false;
        if(n == maxN) {
            res = true;
            fullConfirmed = true;
        }
        semaphore.release();
        return res;
    }

}
