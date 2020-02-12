package com.lab3b;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class BarbershopControl {
    private Semaphore barberSleep;
    private Semaphore barberWork;
    private Semaphore customerSleep;

    BarbershopControl() {
        barberSleep = new Semaphore(1);
        barberWork = new Semaphore(1);
        customerSleep = new Semaphore(1);

        barberSleep();
        customerSleep();
    }

    public void barberSleep() {
       while(!barberSleep.tryAcquire()) {
           Thread.yield();
       }
    }

    public void wakeUpBarber() {
        barberSleep.release();
    }

    public void finishWorkAndInviteCustomer() {
        barberWork.release();
    }

    public void waitForInvitation() {
        while(!barberWork.tryAcquire())
            Thread.yield();
    }

    public void customerSleep() {
        while (!customerSleep.tryAcquire())
            Thread.yield();
    }

    public void wakeUpCustomer() {
        customerSleep.release();
    }




}
