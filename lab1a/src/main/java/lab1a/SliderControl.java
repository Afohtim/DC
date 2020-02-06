package lab1a;

import javafx.scene.control.Slider;

import java.util.concurrent.TimeUnit;

public class SliderControl {

    final private int maxValue = 90;
    final private int minValue = 10;
    final private int timeout = 10;

    private Slider slider;

    SliderControl(Slider slider) {
        this.slider = slider;
    }

    synchronized void increment()
    {
        synchronized (slider) {
            if(slider.getValue() < maxValue)
                slider.increment();
            try {
                TimeUnit.MILLISECONDS.sleep(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    synchronized void decrement() {
        synchronized (slider) {
            if(slider.getValue() > minValue)
                slider.decrement();
            try {
                TimeUnit.MILLISECONDS.sleep(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    synchronized int scrollBarValue() {
        synchronized (slider) {
            return (int) slider.getValue();
        }

    }
}
