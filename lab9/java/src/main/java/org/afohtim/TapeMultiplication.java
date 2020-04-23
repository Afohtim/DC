package org.afohtim;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

public class TapeMultiplication extends RecursiveAction {

    Matrix a;
    Matrix b;
    Matrix c;
    boolean isMainProcess;
    int rowId;
    int columnId;

    TapeMultiplication(Matrix a, Matrix b, Matrix c, boolean isMainProcess, int rowId, int columnId) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.isMainProcess = isMainProcess;
        this.rowId = rowId;
        this.columnId = columnId;
    }

    @Override
    public void compute() {
        List<TapeMultiplication> subTasks = new ArrayList<>();

        if (isMainProcess) {
            for (int i = 0; i < a.size(); ++i) {
                TapeMultiplication task = new TapeMultiplication(a, b, c, false, i, 0);
                task.fork();
                subTasks.add(task);
            }
        }

        if (!isMainProcess) {
            for (int j = 0; j < b.size(); ++j) {
                int res = 0;

                for (int k = 0; k < a.size(); ++k) {
                    res += a.get(rowId, k) * b.get(k, j);
                }

                c.set(rowId, columnId, res);
            }
        }

        for (TapeMultiplication task : subTasks) {
            task.join();
        }
    }
}
