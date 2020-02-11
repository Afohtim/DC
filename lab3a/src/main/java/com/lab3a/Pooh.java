package com.lab3a;

public class Pooh {
    private Pot pot;

    Pooh(Pot pot) {
        this.pot = pot;
    }

    void eat() {
        pot.eatAll();
        System.out.println("Pooh ate all the honey");
    }

}
