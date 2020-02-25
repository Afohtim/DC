package org.afohtim;

import java.util.concurrent.locks.ReentrantLock;

public class LineUpdate {
    private boolean changed;
    ReentrantLock lock;
    int maxN;
    int n;

    LineUpdate(int n){
        changed = true;
        this.maxN = n;
        this.n = n;
        lock = new ReentrantLock();
    }

    boolean isChanged(){
        lock.lock();
        boolean ans =  changed;
        n--;
        if(n == 0) {
            changed = false;
        }
        lock.unlock();
        return ans;
    }

    void changed() {
        lock.lock();
        n = maxN;
        changed = true;
        lock.unlock();
    }
}
