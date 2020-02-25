package org.afohtim;

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LinePiece implements Runnable {
    CyclicBarrier barrier;
    Vector<Integer> directions;
    ReentrantReadWriteLock mutex;
    LinePiece left;
    LinePiece right;
    LineUpdate lineUpdate;

    LinePiece(int size, CyclicBarrier barrier, LineUpdate lineUpdate) {
        directions = new Vector<>();
        mutex = new ReentrantReadWriteLock();
        directions.setSize(size);
        for(int i = 0; i < size; ++i) {
            directions.set(i, 0);
        }

        this.barrier = barrier;
        this.lineUpdate = lineUpdate;
        left = right = null;
    }

    int getLeftElement() {
        int ans;
        mutex.readLock().lock();
        ans = directions.firstElement();
        mutex.readLock().unlock();
        return ans;
    }
    int getRightElement() {
        int ans;
        mutex.readLock().lock();
        ans = directions.lastElement();
        mutex.readLock().unlock();
        return ans;
    }
    
    

    void randomShuffle() {
        mutex.readLock().lock();
        Random random = new Random();
        for(int i = 0; i < directions.size(); ++i) {
            directions.set(i, random.nextInt(2));
        }
        mutex.readLock().unlock();
    }

    void setLeft(LinePiece left) {
        this.left = left;
    }

    void setRight(LinePiece right) {
        this.right = right;
    }

    void printDirections() {
        mutex.readLock().lock();
        for(Integer i : directions) {
            System.out.print(Integer.toString(i) + " ");
        }
        mutex.readLock().unlock();
        //System.out.println();

    }

    
    @Override
    public void run() {
        while(lineUpdate.isChanged()) {
            Vector<Integer> newDirections = new Vector<>();
            mutex.readLock().lock();

            int res = directions.firstElement();
            if(directions.firstElement() == 0) {
                if(left != null) {
                    if(left.getRightElement() != directions.firstElement())
                    {
                        res = (directions.firstElement() + 1 ) % 2;
                    }
                }
            }
            else if(directions.get(1) == 0) {
                res = 0;
            }
            newDirections.add(res);

            for(int i = 1; i < directions.size() - 1; ++i) {
                res = 0;
                if(directions.get(i) == 0 && directions.get(i - 1) == 1 ||
                        directions.get(i) == 1 && directions.get(i + 1) == 1) {
                    res = 1;
                }
                newDirections.add(res);

            }

            res = directions.lastElement();
            if(directions.lastElement() == 1) {
                if(right != null) {
                    if(right.getLeftElement() != directions.lastElement())
                    {
                        res = (directions.lastElement() + 1 ) % 2;
                    }
                }
            }
            else if(directions.get(directions.size() - 2) == 1) {
                res = 1;
            }
            newDirections.add(res);

            mutex.readLock().unlock();

            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }

            mutex.writeLock().lock();
            for(int i = 0; i < directions.size(); ++i) {
                if(!directions.get(i).equals(newDirections.get(i))) {
                    lineUpdate.changed();
                }
                directions.set(i, newDirections.get(i));
            }
            mutex.writeLock().unlock();
            try {
                //System.out.print("e");
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}

