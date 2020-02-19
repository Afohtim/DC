package org.afohtim.lab4a;

public abstract class Lockable<Locker extends ReadWriteLockInterface> implements Runnable {

    protected Locker locker;

    abstract protected void lock();
    abstract protected void unlock();
    abstract protected void lockedRun();

    @Override
    public void run() {
        lock();
        lockedRun();
        unlock();
    }
}
