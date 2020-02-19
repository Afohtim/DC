package org.afohtim.lab4a;

public abstract class Reader<Locker extends ReadWriteLockInterface> extends Lockable<Locker> {
    @Override
    protected void lock() {
        locker.readLock();
    }

    @Override
    protected void unlock() {
        locker.readUnlock();
    }

}
