package org.afohtim;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        List<Integer> sizes = new ArrayList<>();

        int size = 100;
        Matrix a = new Matrix();
        Matrix b = new Matrix();
        Matrix c = new Matrix();

        a.randomFill(size);
        b.randomFill(size);
        c.zeros(size);


        long start = System.currentTimeMillis();

        new ForkJoinPool().invoke(new TapeMultiplication(a, b, c, true, 0, 0));

        long end = System.currentTimeMillis();

        System.out.println(end - start);

        c.zeros(size);

        start = System.currentTimeMillis();

        ConsecutiveMultiplication.multiply(a, b, c);

        end = System.currentTimeMillis();

        System.out.println(end - start);




    }
}