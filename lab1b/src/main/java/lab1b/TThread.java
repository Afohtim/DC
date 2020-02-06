package lab1b;

public class TThread implements Runnable {
    private boolean isIncrementing;
    private SliderControl sliderControl;

    TThread(SliderControl sliderControl, boolean isIncrementing) {
        this.sliderControl = sliderControl;
        this.isIncrementing = isIncrementing;
    }

    public void run(){

        while (!Thread.currentThread().isInterrupted()){
            if(isIncrementing) {
            sliderControl.increment();
            } else {
            sliderControl.decrement();
            }
        }
    }

}
