package org.lab2a;


public class Main {
    public static Field field;
    public static void main(String[] args) {
        field = new Field(10, 10);
        Thread bees1 = new Thread(new BeeSearch(field, 0,0,5,5));
        Thread bees2 = new Thread(new BeeSearch(field, 0,5,5,10));
        Thread bees3 = new Thread(new BeeSearch(field, 5,0,10,5));
        Thread bees4 = new Thread(new BeeSearch(field, 5,5,10,10));

        bees1.start();
        bees2.start();
        bees3.start();
        bees4.start();
    }
}
