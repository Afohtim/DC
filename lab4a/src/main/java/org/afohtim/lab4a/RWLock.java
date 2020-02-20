package org.afohtim.lab4a;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLock implements ReadWriteLockInterface {

    ReentrantReadWriteLock locker;

    RWLock() {
        locker = new ReentrantReadWriteLock();
    }

    @Override
    public void readLock() {
        locker.readLock().lock();
    }
    @Override
    public void readUnlock() {
        locker.readLock().unlock();
    }
    @Override
    public void writeLock() {
        locker.writeLock().lock();
    }
    @Override
    public void writeUnlock() {
        locker.writeLock().unlock();
    }
}
