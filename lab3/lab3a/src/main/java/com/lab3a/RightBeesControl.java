package com.lab3a;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RightBeesControl implements Runnable{

    ArrayList<Bee> bees;
    Pooh pooh;
    Pot pot;
    ExecutorService executorService;

    RightBeesControl(int countOfBees, int potSize) {
        pot = new Pot(potSize);
        pooh = new Pooh(pot);
        bees = new ArrayList<>();
        for(int i = 0; i < countOfBees; ++i) {
            bees.add(new Bee(i, pot, pooh));
        }
        executorService = Executors.newFixedThreadPool(countOfBees);
    }

    @Override
    public void run() {
        for (Bee bee : bees) {
            executorService.submit(bee);
        }

    }
}
