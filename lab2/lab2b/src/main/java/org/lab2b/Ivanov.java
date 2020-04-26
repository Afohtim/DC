package org.lab2b;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Ivanov implements Runnable {
    private ArrayList<Integer> storage;
    ConcurrentLinkedQueue<Integer> queue;
    int maxQueueN;

    Ivanov(ArrayList<Integer> storage, ConcurrentLinkedQueue<Integer> queue, int maxQueueN) {
        this.storage = storage;
        this.queue = queue;
        this.maxQueueN = maxQueueN;
    }

    @Override
    public void run() {
        int n = storage.size();
        for(int i = 0; i < n; ++i) {
            while (queue.size() == maxQueueN) {
                Thread.yield();
            }
            queue.add(storage.get(0));
            storage.remove(0);
        }
    }
}
