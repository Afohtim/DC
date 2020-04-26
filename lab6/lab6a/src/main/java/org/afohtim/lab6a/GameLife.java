package org.afohtim.lab6a;


import java.util.Vector;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//GameLifeOW
public class GameLife {
    Vector<Region> regions;
    final int numberOfRegions = 4;
    CyclicBarrier barrier;
    ExecutorService executor;
    JavaFXDumbSync javaFXDumbSync;

    public GameLife(Vector<Vector<SquareControl>> squareControls) {
        this.regions = new Vector<>();
        this.barrier = new CyclicBarrier(numberOfRegions);
        this.executor = Executors.newFixedThreadPool(numberOfRegions);
        this.javaFXDumbSync = new JavaFXDumbSync();
        for(int regionId = 0; regionId < numberOfRegions; ++regionId) {
                Vector<Vector<SquareControl>> regionSquares = new Vector<>();
            for(int i = 0; i < squareControls.size() / 2; ++i) {
                regionSquares.add(new Vector<>());
                for(int j = 0; j < squareControls.size() / 2; ++j) {
                    regionSquares.lastElement().add(squareControls.get((squareControls.size() / 2 * (regionId / 2) + i)).get(squareControls.size() / 2  * (regionId % 2) + j));
                }
            }
            regions.add(new Region(regionSquares, javaFXDumbSync));
        }
        regions.get(0).setUpRegion(null, regions.get(1), regions.get(2), null, barrier);
        regions.get(1).setUpRegion(null, null, regions.get(3), regions.get(0), barrier);
        regions.get(2).setUpRegion(regions.get(0), regions.get(3), null, null, barrier);
        regions.get(3).setUpRegion(regions.get(1), null, null, regions.get(2), barrier);
    }
    public void start(){
        for(int i = 0; i < numberOfRegions; ++i) {
            executor.submit(regions.get(i));
        }
    }

    public void stop(){
        executor.shutdownNow();
        executor = Executors.newFixedThreadPool(numberOfRegions);
    }
}
