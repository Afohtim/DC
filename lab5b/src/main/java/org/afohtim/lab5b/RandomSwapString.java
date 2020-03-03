package org.afohtim.lab5b;

import java.util.Random;

public class RandomSwapString implements Runnable {
    private StringBuilder content;
    private StringCheckBarrier barrier;


    public RandomSwapString(StringCheckBarrier barrier, int len) {
        this.barrier = barrier;
        this.content = new StringBuilder();
        generateString(len);

    }

    private void generateString(int len) {
        Random random = new Random();
        for(int i = 0; i < len; ++i) {
            content.append(Character.toChars('a' + random.nextInt(4)));
        }
    }

    int charCount(char c) {
        int count = 0;
        for(int i = 0; i < content.length(); ++i) {
            if(content.charAt(i) == c)
                count++;
        }
        return count;
    }

    private void changeChar(int id) {
        switch (content.charAt(id)) {
            case 'a' :
                content.setCharAt(id, 'c');
                break;
            case 'b' :
                content.setCharAt(id, 'd');
                break;
            case 'c':
                content.setCharAt(id, 'a');
                break;
            case 'd':
                content.setCharAt(id, 'b');
        }
    }

    @Override
    public void run() {
        while(!barrier.stringsAreInBalance(charCount('a'), charCount('b'))) {
            Random random = new Random();
            if(random.nextBoolean()) {
                int id = random.nextInt(content.length());
                changeChar(id);
            }

        }
    }
}
