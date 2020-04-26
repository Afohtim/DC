package lab1b;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Idle implements Runnable {
    private int i = 0;

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted())
        {
            i++;
        }
    }
}

public class WindowControl {

    @FXML
    private Slider slider;

    @FXML
    private TextField priority1;

    @FXML
    private TextField priority2;

    private SliderControl sliderControl;
    private ThreadControl threadControl;

    @FXML
    public void initialize() {
            sliderControl = new SliderControl(slider);
            threadControl = new ThreadControl(sliderControl);
    }


    public void processorKiller(ActionEvent event) {
        int n = 10;
        ExecutorService pKillerPool = Executors.newFixedThreadPool(n);
        for(int i = 0; i < n; ++i)
        {
            Thread idle = new Thread(new Idle());
            idle.setDaemon(true);
            idle.setPriority(1);
            idle.start();
        }

    }

    public void start1(ActionEvent event) {
        threadControl.startThread(1);
    }
    public void start2(ActionEvent event) {
        threadControl.startThread(2);
    }
    public void stop1(ActionEvent event) {
        threadControl.stopThread(1);
    }

    public void stop2(ActionEvent event) {
        threadControl.stopThread(2);
    }


}
