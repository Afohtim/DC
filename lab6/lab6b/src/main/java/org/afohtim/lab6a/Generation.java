package org.afohtim.lab6a;

import javafx.scene.paint.Color;

import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class Generation implements Runnable {



    Vector<Vector<SquareControl>> squares;
    Vector<Vector<Boolean>> buffer;
    CyclicBarrier barrier;
    JavaFXDumbSync javaFXDumbSync;
    Color color;

    Generation(Vector<Vector<SquareControl>> squares, CyclicBarrier barrier, JavaFXDumbSync javaFXDumbSync) {
        this.squares = squares;
        this.barrier = barrier;
        this.javaFXDumbSync = javaFXDumbSync;
        this.buffer = new Vector<>();
        for(int i = 0; i < squares.size(); ++i) {
            buffer.add(new Vector<>());
            for(int j = 0; j < squares.size(); ++j) {
                buffer.lastElement().add(false);
            }
        }

    }

    void setColor(Color color) {
        this.color = color;
    }

    private int getNearbySum(int x, int y) {
        int sum = squares.get(x).get(y).isAlive() ? -1 : 0;


        for(int i = Math.max(x - 1, 0); i < Math.min(x + 2, squares.size()); ++i) {
            for(int j = Math.max(y - 1, 0); j < Math.min(y + 2, squares.size()); ++j) {
                sum += squares.get(i).get(j).isAlive() && squares.get(i).get(j).getColor() == color ? 1 : 0;
            }
        }
        return sum;
    }


    @Override
    public void run(){
        while(!Thread.currentThread().isInterrupted()) {
            for(int i = 0; i < squares.size(); ++i) {
                for(int j = 0; j < squares.size(); ++j) {
                    buffer.get(i).set(j, squares.get(i).get(j).isAlive());
                    int sum = getNearbySum(i, j);
                    if(squares.get(i).get(j).isAlive()) {
                        if(sum != 2 && sum != 3) {
                            buffer.get(i).set(j, false);
                        }
                    } else {
                        if(sum == 3) {
                            buffer.get(i).set(j, true);
                        }
                    }
                }
            }


            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                continue;
            }
            //I'm too tired at this point so here is a "solution" of computing of the field. yes. resetting 4 times
            // and recomputing 4 times. Cool. Just end this suffering
            for(int i = 0; i < buffer.size(); ++i)
                for (int j = 0; j < buffer.get(i).size(); ++j) {
                    javaFXDumbSync.lock();
                    squares.get(i).get(j).setAlive(false, null);
                    javaFXDumbSync.unlock();
                }

            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                continue;
            }

            for(int i = 0; i < buffer.size(); ++i)
                for (int j = 0; j < buffer.get(i).size(); ++j) {
                    javaFXDumbSync.lock();
                    if(buffer.get(i).get(j)) {
                        if(!squares.get(i).get(j).isAlive()) {
                            squares.get(i).get(j).setAlive(buffer.get(i).get(j), color);
                        }
                    }
                    javaFXDumbSync.unlock();
                }

            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                continue;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
