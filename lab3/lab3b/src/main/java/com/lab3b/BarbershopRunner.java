package com.lab3b;


import java.util.concurrent.TimeUnit;

public class BarbershopRunner implements Runnable {

    @Override
    public void run(){
        BarbershopControl control = new BarbershopControl();
        Barber barber = new Barber(control);
        Thread barberThread = new Thread(barber);
        barberThread.setDaemon(true);
        barberThread.start();

        for(int i = 0; i < 15; ++i) {
            Thread customerThread = new Thread(new Customer(i, control));
            customerThread.setDaemon(true);
            customerThread.start();

            if(i == 9) {
                try {
                    TimeUnit.MILLISECONDS.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            TimeUnit.MILLISECONDS.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
