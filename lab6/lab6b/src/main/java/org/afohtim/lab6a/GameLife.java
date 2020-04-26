package org.afohtim.lab6a;


import javafx.scene.paint.Color;

import java.util.Vector;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//GameLifeOW
public class GameLife {
    Vector<Generation> generations;
    final int numberOfRegions = 4;
    CyclicBarrier barrier;
    ExecutorService executor;
    JavaFXDumbSync javaFXDumbSync;


    public GameLife(Vector<Vector<SquareControl>> squareControls) {
        this.generations = new Vector<>();
        this.barrier = new CyclicBarrier(numberOfRegions);
        this.executor = Executors.newFixedThreadPool(numberOfRegions);
        this.javaFXDumbSync = new JavaFXDumbSync();
        for(int generationId = 0; generationId < numberOfRegions; ++generationId) {
            generations.add(new Generation(squareControls, barrier, javaFXDumbSync));
        }
        for(int i = 0; i < generations.size(); ++i) {
            generations.get(i).setColor(ColorPalette.color(i));
        }
    }

    public void start(){
        for(int i = 0; i < numberOfRegions; ++i) {
            executor.submit(generations.get(i));
        }
    }

    public void stop(){
        executor.shutdownNow();
        executor = Executors.newFixedThreadPool(numberOfRegions);
    }
}
