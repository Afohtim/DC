package org.afohtim.lab4a;

public class Writer<Locker extends ReadWriteLockInterface> extends Lockable<Locker> {
    Action action;

    Writer(Locker locker, Action action) {
        this.locker = locker;
        this.action = action;
    }

    @Override
    protected void lock() {
        locker.writeLock();
    }

    @Override
    protected void unlock() {
        locker.writeUnlock();
    }

    @Override
    protected String lockedRun() {
        return action.act();
    }

}
