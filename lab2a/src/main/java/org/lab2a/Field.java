package org.lab2a;

import java.util.ArrayList;

public class Field {

    ArrayList<ArrayList<Integer>> field;
    Pooh pooh;
    Thread poohThread;


    Field(int x, int y) {
        pooh = new Pooh(x, y);
        poohThread = new Thread(pooh);
        poohThread.start();
    }

    boolean search(int x, int y) {
        if(pooh.x == x && pooh.y == y) {
            poohThread.interrupt();
            return true;
        }
        return false;
    }

}
