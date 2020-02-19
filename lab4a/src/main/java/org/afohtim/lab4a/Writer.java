package org.afohtim.lab4a;

public abstract class Writer<Locker extends ReadWriteLockInterface> extends Lockable<Locker> {
    @Override
    protected void lock() {
        locker.writeLock();
    }

    @Override
    protected void unlock() {
        locker.readLock();
    }

}
