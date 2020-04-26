package com.lab3a;

import java.util.concurrent.TimeUnit;

public class Bee implements Runnable {

    private int sleepTime = 300;
    private Pot pot;
    private Pooh pooh;
    private int id;

    Bee(int id, Pot pot, Pooh pooh) {
        this.pot = pot;
        this.pooh = pooh;
        this.id = id;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted())
        {
            System.out.println(String.format("Bee #%d is putting a portion of honey", id));
            if(pot.put()) {
                pooh.eat();
            }
            try {
                TimeUnit.MILLISECONDS.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
