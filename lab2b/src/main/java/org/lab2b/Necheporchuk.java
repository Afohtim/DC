package org.lab2b;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Necheporchuk implements Runnable {
    ConcurrentLinkedQueue<Integer> queue1;
    ConcurrentLinkedQueue<Integer> queue2;
    int maxQueueN;
    int inStorage;

    int sum;

    Necheporchuk(ConcurrentLinkedQueue<Integer> queue1, ConcurrentLinkedQueue<Integer> queue2, int maxQueueN, int inStorage) {
        this.queue1 = queue1;
        this.queue2 = queue2;
        this.maxQueueN = maxQueueN;
        this.inStorage = inStorage;
    }

    @Override
    public void run() {

        for(int i = 0; i < inStorage; ++i) {
            while (queue2.size() == maxQueueN || queue1.isEmpty()) {
                Thread.yield();
            }
            sum += queue1.peek();
            queue2.add(queue1.poll());
        }



    }

    public int getSum() {
        return sum;
    }

}
