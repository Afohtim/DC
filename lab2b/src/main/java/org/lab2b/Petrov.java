package org.lab2b;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Petrov implements Runnable {
    ConcurrentLinkedQueue<Integer> queue;
    int inStorage;
    int maxQueueN;
    ArrayList<Integer> truck;

    Petrov(ConcurrentLinkedQueue<Integer> queue, ArrayList<Integer> truck, int maxQueueN, int inStorage) {
        this.queue = queue;
        this.inStorage = inStorage;
        this.maxQueueN = maxQueueN;
        this.truck = truck;
    }

    @Override
    public void run() {
        for(int i = 0; i < inStorage; ++i) {
            while (queue.isEmpty()) {
                Thread.yield();
            }
            truck.add(queue.poll());
        }
    }

}
