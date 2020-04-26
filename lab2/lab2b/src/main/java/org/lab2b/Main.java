package org.lab2b;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class Main {

    static void print(ArrayList<Integer> arr) {
        for (Integer integer : arr) {
            System.out.print(integer);
            System.out.print(' ');
        }
        System.out.println();
    }

    static int sumArr(ArrayList<Integer> arr) {
        int sum = 0;
        for (Integer integer : arr) {
            sum += integer;
        }
        return sum;
    }

    public static void main(String[] args) {
        int inStorage = 15;
        int maxQueueN = 5;
        ArrayList<Integer> storage = new ArrayList<>();
        ArrayList<Integer> truck = new ArrayList<>();
        Random randomGen = new Random();
        for(int i = 0; i < inStorage; ++i) {
            storage.add(randomGen.nextInt(100));
        }

        print(storage);
        System.out.println(sumArr(storage));



        ConcurrentLinkedQueue<Integer> queue1 = new ConcurrentLinkedQueue<Integer>();
        ConcurrentLinkedQueue<Integer> queue2 = new ConcurrentLinkedQueue<Integer>();

        Ivanov ivanov = new Ivanov(storage, queue1, maxQueueN);
        Necheporchuk necheporchuk = new Necheporchuk(queue1, queue2, maxQueueN, inStorage);
        Petrov petrov = new Petrov(queue2, truck, maxQueueN, inStorage);

        Thread thread1 = new Thread(ivanov);
        Thread thread2 = new Thread(necheporchuk);
        Thread thread3 = new Thread(petrov);

        thread1.setDaemon(true);
        thread2.setDaemon(true);
        thread3.setDaemon(true);

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        print(truck);
        System.out.println(necheporchuk.sum);

    }
}
