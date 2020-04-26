package org.afohtim.lab4a;

import java.util.concurrent.Callable;

public abstract class Lockable<Locker extends ReadWriteLockInterface> implements Callable<String> {

    protected Locker locker;

    abstract protected void lock();
    abstract protected void unlock();
    abstract protected String lockedRun();

    @Override
    public String call() {
        lock();
        String ans = lockedRun();
        unlock();
        return ans;
    }
}
