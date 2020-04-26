package org.afohtim.lab6a;

import java.util.concurrent.locks.ReentrantLock;

public class JavaFXDumbSync {
    private ReentrantLock lock;

    JavaFXDumbSync() {
        lock = new ReentrantLock();
    }

    void lock(){
        lock.lock();
    }
    void unlock(){
        lock.unlock();
    }
}
