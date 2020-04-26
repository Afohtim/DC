package lab1b;

import javafx.scene.control.Slider;

import java.util.concurrent.TimeUnit;

public class SliderControl {

    final private int maxValue = 90;
    final private int minValue = 10;
    final private int timeout = 5;

    private Slider slider;

    SliderControl(Slider slider) {
        this.slider = slider;
    }

    synchronized void increment()
    {
        {
            if(slider.getValue() < maxValue)
                slider.increment();
            try {
                TimeUnit.MILLISECONDS.sleep(timeout);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

    synchronized void decrement() {
        {
            if(slider.getValue() > minValue)
                slider.decrement();
            try {
                TimeUnit.MILLISECONDS.sleep(timeout);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

    synchronized int scrollBarValue() {
        synchronized (slider) {
            return (int) slider.getValue();
        }

    }
}
