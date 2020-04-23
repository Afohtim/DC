package org.afohtim;

import java.util.Random;

public class Matrix {
    int[] matrix;
    int size;

    void randomFill(int size) {
        matrix = new int[size * size];
        this.size = size;
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i * size + j] = random.nextInt(10) + 1;  //from 1 to 10
            }
        }
    }

    void zeros(int size) {
        matrix = new int[size * size];
        this.size = size;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i * size + j] = 0;
            }
        }
    }

    void transpose() {
        for (int i = 0; i < size; i++) {
            for (int j = i; j < size; j++) {
                int temp = matrix[i * size + j];
                matrix[i * size + j] = matrix[j * size + i];
                matrix[j * size + i] = temp;
            }
        }
    }

    int get(int i, int j) {
        return matrix[i * size + j];
    }

    void set(int i, int j, int val) {
        matrix[i * size + j] = val;
    }

    int size() {
        return size;
    }
}