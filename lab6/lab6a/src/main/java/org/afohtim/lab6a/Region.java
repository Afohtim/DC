package org.afohtim.lab6a;

import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;


//Region is expected to be square. Further changes are needed to make it rectangle
public class Region implements Runnable {
        Vector<Vector<SquareControl>> squares;
    Vector<Vector<Boolean>> buffer;
    Region upRegion;
    Region rightRegion;
    Region downRegion;
    Region leftRegion;
    CyclicBarrier barrier;
    ReentrantLock lock;
    enum positions {Middle, Up, Right, Down, Left}
    JavaFXDumbSync javaFXDumbSync;

    Region(Vector<Vector<SquareControl>> squares, JavaFXDumbSync javaFXDumbSync) {
        this.squares = squares;
        this.javaFXDumbSync = javaFXDumbSync;
    }

    Region(Vector<Vector<SquareControl>> squares, Region up, Region right, Region down, Region left, CyclicBarrier barrier) {
        this.squares = squares;
        this.lock = new ReentrantLock();
        setUpRegion(up, right, down, left, barrier);

    }

    void setUpRegion(Region up, Region right, Region down, Region left, CyclicBarrier barrier) {
        this.upRegion = up;
        this.rightRegion = right;
        this.downRegion = down;
        this.leftRegion = left;
        buffer = new Vector<>();
        for(int i = 0; i < squares.size(); ++i) {
            buffer.add(new Vector<>());
            for(int j = 0; j < squares.size(); ++j) {
                buffer.lastElement().add(false);
            }
        }
        this.barrier = barrier;
    }

    boolean getSquareState(int i, int j) {
        return squares.get(i).get(j).isAlive();
    }

    private int getNearbySum(int x, int y) {
        int sum = squares.get(x).get(y).isAlive() ? -1 : 0;


        for(int i = Math.max(x - 1, 0); i < Math.min(x + 2, squares.size()); ++i) {
            for(int j = Math.max(y - 1, 0); j < Math.min(y + 2, squares.size()); ++j) {
                sum += squares.get(i).get(j).isAlive() ? 1 : 0;
            }
        }

        if (x == 0 && upRegion != null) {
            sum += upRegion.getSum(positions.Down, y);
        }
        if (x == squares.size() - 1 && downRegion != null) {
            sum += downRegion.getSum(positions.Up, y);
        }
        if (y == 0 && leftRegion != null) {
            sum += leftRegion.getSum(positions.Right, x);
        }
        if (y == squares.size() - 1 && rightRegion != null) {
            sum += rightRegion.getSum(positions.Left, x);
        }

        if(x == 0 && y == 0 && upRegion != null && upRegion.leftRegion != null) {
            sum += upRegion.leftRegion.isCornerAlive(positions.Down, positions.Right) ? 1 : 0;
        }
        if(x == 0 && y == squares.size() - 1 && upRegion != null && upRegion.rightRegion != null) {
            sum += upRegion.rightRegion.isCornerAlive(positions.Down, positions.Left) ? 1 : 0;
        }
        if(x == squares.size() - 1 && y == 0 && downRegion != null && downRegion.leftRegion != null) {
            sum += downRegion.leftRegion.isCornerAlive(positions.Up, positions.Right) ? 1 : 0;
        }
        if(x == squares.size() - 1 && y == squares.size() - 1 && downRegion != null && downRegion.rightRegion != null) {
            sum += downRegion.rightRegion.isCornerAlive(positions.Up, positions.Left) ? 1 : 0;
        }

        return sum;
    }

    int getSum(positions position, int coordinate) {
        int sum = 0;
        for(int i = Math.max(coordinate - 1, 0); i < Math.min(coordinate + 2, squares.size()); ++i) {
            switch (position) {
                case Up:
                    sum += squares.firstElement().get(i).isAlive() ? 1 : 0;
                    break;
                case Right:
                    sum += squares.get(i).lastElement().isAlive() ? 1 : 0;
                    break;
                case Down:
                    sum += squares.lastElement().get(i).isAlive() ? 1 : 0;
                    break;
                case Left:
                    sum += squares.get(i).firstElement().isAlive() ? 1 : 0;
                    break;
            }
        }
        /*switch (position) {
            case Up:
                if(coordinate == 0 && leftRegion != null) {
                    sum += leftRegion.isCornerAlive(positions.Up, positions.Right) ? 1 : 0;
                }
                if(coordinate == 19 && rightRegion != null) {
                    sum += rightRegion.isCornerAlive(positions.Up, positions.Left) ? 1 : 0;
                }
                break;
            case Right:
                if(coordinate == 0 && upRegion != null) {
                    sum += upRegion.isCornerAlive(positions.Down, positions.Right) ? 1 : 0;
                }
                if(coordinate == 19 && downRegion != null) {
                    sum += downRegion.isCornerAlive(positions.Up, positions.Right) ? 1 : 0;
                }
                break;
            case Down:
                if(coordinate == 0 && leftRegion != null) {
                    sum += leftRegion.isCornerAlive(positions.Down, positions.Right) ? 1 : 0;
                }
                if(coordinate == 19 && rightRegion != null) {
                    sum += rightRegion.isCornerAlive(positions.Down, positions.Left) ? 1 : 0;
                }
                break;
            case Left:
                if(coordinate == 0 && upRegion != null) {
                    sum += upRegion.isCornerAlive(positions.Down, positions.Left) ? 1 : 0;
                }
                if(coordinate == 19 && downRegion != null) {
                    sum += downRegion.isCornerAlive(positions.Up, positions.Left) ? 1 : 0;
                }
                break;
        }

         */

        return sum;
    }

    boolean isCornerAlive(positions verticalPosition, positions horizontalPosition) {
        int i = verticalPosition == positions.Up ? 0 : squares.size() - 1;
        int j = horizontalPosition == positions.Left ? 0 : squares.size() - 1;
        return squares.get(i).get(j).isAlive();
    }

    @Override
    public void run() {
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
            }

            for(int i = 0; i < buffer.size(); ++i)
                for (int j = 0; j < buffer.get(i).size(); ++j) {
                    javaFXDumbSync.lock();
                    squares.get(i).get(j).setAlive(buffer.get(i).get(j));
                    javaFXDumbSync.unlock();
                }
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
