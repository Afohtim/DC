package org.afohtim.lab4a;

public class Reader<Locker extends ReadWriteLockInterface> extends Lockable<Locker> {

    Action action;

    Reader(Locker locker, Action action) {
        this.locker = locker;
        this.action = action;
    }

    @Override
    protected void lock() {
        locker.readLock();
    }

    @Override
    protected void unlock() {
        locker.readUnlock();
    }

    @Override
    protected String lockedRun() {
        return action.act();
    }

}
