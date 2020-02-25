package org.afohtim.lab4b;

import java.util.Vector;

public class Garden {

    private Vector<Vector<Integer>> field;
    private ReadWriteLockInterface rwLock;




    Garden(int size, ReadWriteLockInterface rwLock) {
        field = new Vector<Vector<Integer>>(size);

        for(int i = 0; i < size; ++i) {
            Vector<Integer> x = new Vector<Integer>(size);
            for(int j = 0; j < size; ++j) {
                x.add(0);
            }
            field.add(x);
        }
        if(rwLock != null)
            this.rwLock = rwLock;
        else
        {
            this.rwLock = new RWLock();
        }
    }

    void setPlantStatus(int i, int j, int status) {
        rwLock.writeLock();
        Vector<Integer> a =  field.get(j);
        a.set(i, status);
        rwLock.writeUnlock();
    }

    int getPlantStatus(int i, int j) {
        rwLock.readLock();
        int ans = field.get(i).get(j);
        rwLock.readUnlock();
        return ans;
    }

    int getSize() {
        return field.size();
    }

    void readLock() {
        rwLock.readLock();
    }

    void readUnlock() {
        rwLock.readUnlock();
    }
}
