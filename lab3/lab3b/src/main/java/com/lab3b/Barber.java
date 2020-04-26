package com.lab3b;

import java.util.concurrent.TimeUnit;

public class Barber implements Runnable {

    private BarbershopControl control;

    Barber(BarbershopControl control) {
        this.control = control;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            control.barberSleep();

            System.out.println("Barber woke up and started work");


            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }

            System.out.println("Barber finished work, invited customer and fell asleep");

            control.wakeUpCustomer();
            control.finishWorkAndInviteCustomer();
        }
    }
}
