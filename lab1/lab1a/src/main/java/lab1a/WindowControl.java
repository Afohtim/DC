package lab1a;

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

    //ExecutorService pool = Executors.newFixedThreadPool(2);
    Thread thread1;
    Thread thread2;

    private volatile boolean isStarted = false;

    private int priority(int pr) {
        if(pr > 10) pr = 10;
        if(pr < 1) pr = 1;
        return pr;
    }


    public void start(ActionEvent event)
    {
        if(!isStarted) {
            sliderControl = new SliderControl(slider);
            thread1 = new Thread( new TThread(sliderControl, true));
            thread2 = new Thread( new TThread(sliderControl, false));
            thread1.setDaemon(true);
            thread2.setDaemon(true);
        }


        int pr1, pr2;
        try {
            pr1 = priority(Integer.parseInt(priority1.getText()));
            pr2 = priority(Integer.parseInt(priority2.getText()));


        }
        catch (Exception e) {
            pr1 = 1;
            pr2 = 10;
        }
        thread1.setPriority(pr1);
        thread2.setPriority(pr2);


        if(!isStarted) {
            thread1.start();
            thread2.start();
            isStarted = true;
        }



        //pool.execute(thread1);
        //pool.execute(thread2);

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

    @FXML
    public void exitApplication(ActionEvent event) {
        //pool.
        //pool.shutdown();
    }
}
