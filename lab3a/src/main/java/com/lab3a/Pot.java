package com.lab3a;


public class Pot {
    private int semaphore;
    private int n;
    private int maxN;
    private boolean fullConfirmed;

    Pot(int n) {
        semaphore = 1;
        this.maxN = n;
        this.n = 0;
    }

    boolean tryPut(){
        if(n < maxN)
        {
            if(tryAcquire()) {
                n++;
                release();
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
            if(tryAcquire()) {
                n = 0;
                fullConfirmed = false;
                release();
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
        while(tryAcquire());
        boolean res = false;
        if(n == maxN) {
            res = true;
            fullConfirmed = true;
        }
        release();
        return res;
    }

    public synchronized boolean tryAcquire() {
        if(semaphore == 0) {
            return false;
        }
        else {
            semaphore = 0;
            return true;
        }
    }

    public synchronized void release() {
        semaphore = 1;
    }

}
