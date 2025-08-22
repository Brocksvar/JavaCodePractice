package org.example.javaconcurrency.complextask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class ComplexTaskExecutor {


    private final int numberOfThreads;

    public ComplexTaskExecutor(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public void executeTasks(int numberOfTasks) {
        if (numberOfTasks <= 0) return;
        List<Integer> resultList = Collections.synchronizedList(new ArrayList<>());
        Runnable finalAction = () -> {
            int result = 0;
            for (int res : resultList) {
                result += res;
            }
            System.out.println("Общий результат: " + result);
        };
        CyclicBarrier cyclicBarrier = new CyclicBarrier(numberOfThreads, finalAction);
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfTasks);

        for (int i = 0; i < numberOfTasks; i++) {
            Runnable runnableTask = () -> {
                try {
                    ComplexTask complexTask = new ComplexTask();
                    int resultsPart = complexTask.execute();
                    resultList.add(resultsPart);
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                }
            };
            executorService.submit(runnableTask);
        }
        executorService.shutdown();
    }
}
