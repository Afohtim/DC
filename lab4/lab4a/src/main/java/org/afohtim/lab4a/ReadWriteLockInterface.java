package org.afohtim.lab4a;

public interface ReadWriteLockInterface {
    void readLock();
    void readUnlock();
    void writeLock();
    void writeUnlock();
}
