package org.example.javastreamapi.factorial;

import java.util.concurrent.RecursiveTask;

public class FactorialTask extends RecursiveTask<Long> {
    private final int number;

    public FactorialTask(int number) {
        this.number = number;
    }

    @Override
    protected Long compute() {
        long factorial;

        if (number - 1 < 1) {
            factorial = 1;
        } else {
            FactorialTask subTask = new FactorialTask(number - 1);

            subTask.fork();

            factorial = (long) number * subTask.join();
        }

        return factorial;
    }
}
