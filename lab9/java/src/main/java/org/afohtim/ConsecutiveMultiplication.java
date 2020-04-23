package org.afohtim;

public final class ConsecutiveMultiplication {

    static void multiply(Matrix a, Matrix b, Matrix c) {
        for(int i = 0; i < a.size(); ++i) {
            for(int j = 0; j < b.size(); ++j) {
                int res = 0;
                for(int k = 0; k < a.size(); ++k) {
                    res += a.get(i, k) * b.get(k, j);
                }
                c.set(i, j, res);
            }
        }
    }

}
