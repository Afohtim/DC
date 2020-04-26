package lab1b;

import java.util.concurrent.Semaphore;

public class ThreadControl {
    private Semaphore semaphore;

    Thread thread1;
    Thread thread2;
    private SliderControl sliderControl;

    ThreadControl(SliderControl sliderControl) {
        this.sliderControl = sliderControl;
        semaphore = new Semaphore(1);
    }

    public void startThread(int threadId) {
        if(semaphore.tryAcquire()) {
            if(threadId == 1) {
                thread1 = new Thread( new TThread(sliderControl, true));
                thread1.setDaemon(true);
                thread1.setPriority(1);
                thread1.start();
            }
            else {
                thread2 = new Thread( new TThread(sliderControl, false));
                thread2.setDaemon(true);
                thread2.setPriority(10);
                thread2.start();
            }
        }
    }

    public void stopThread(int threadId) {
        if(threadId == 1 && thread1.isAlive()) {
            thread1.interrupt();
            semaphore.release();
        }
        else if(threadId == 2 && thread2.isAlive()) {
            thread2.interrupt();
            semaphore.release();
        }


    }
}
