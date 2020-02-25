package org.afohtim.lab4b;

public interface ReadWriteLockInterface {
    void readLock();
    void readUnlock();
    void writeLock();
    void writeUnlock();
}
