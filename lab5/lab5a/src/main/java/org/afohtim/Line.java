package org.afohtim;

import java.util.Vector;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Line {
    Vector<LinePiece> pieces;
    CyclicBarrier barrier;
    ExecutorService executor;
    LineUpdate lineUpdate;

    Line(int piecesCount, int elementsInPieces) {
        pieces = new Vector<>();
        barrier = new CyclicBarrier(piecesCount);
        lineUpdate = new LineUpdate(piecesCount);
        executor = Executors.newFixedThreadPool(piecesCount);
        pieces.add(new LinePiece(elementsInPieces, barrier, lineUpdate));
        for(int i = 1; i < piecesCount; ++i) {
            pieces.add(new LinePiece(elementsInPieces, barrier, lineUpdate));
            pieces.get(i).setLeft(pieces.get(i-1));
            pieces.get(i-1).setRight(pieces.get(i));
        }
    }

    void randomShuffle() {
        for (LinePiece piece : pieces) {
            piece.randomShuffle();
        }
    }

    void printDirections() {
        for(LinePiece piece : pieces) {
            piece.printDirections();
        }
        System.out.println();
    }

    void run() {
        printDirections();
        for (LinePiece piece : pieces) {
            executor.submit(piece);
        }

        while (executor.isShutdown()) {
            Thread.yield();
        }

        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printDirections();
    }

}
