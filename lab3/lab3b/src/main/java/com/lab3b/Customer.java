package com.lab3b;


public class Customer implements Runnable {

    private BarbershopControl control;
    private int id;


    Customer(int id, BarbershopControl control) {
        this.id = id;
        this.control = control;
    }

    @Override
    public void run() {
        control.waitForInvitation();
        System.out.println(String.format("Customer %d is invited and fell asleep", id));
        control.wakeUpBarber();
        control.customerSleep();
        System.out.println(String.format("Customer %d woke up and is going away", id));
    }
}
