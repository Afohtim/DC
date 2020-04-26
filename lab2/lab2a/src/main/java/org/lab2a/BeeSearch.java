package org.lab2a;

public class BeeSearch implements Runnable {

    int x1, y1, x2, y2;
    final Field field;

    BeeSearch(Field field, int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.field = field;
    }

    @Override
    public void run() {
        synchronized (field) {
            for(int i = y1; i < y2; ++i) {
                for(int j = x1; j < x2; ++j) {
                    if(field.search(j, i)) {
                        System.out.println(String.format("Found Pooh at (%d, %d)", j, i));
                    }
                }
            }
        }
    }
}
