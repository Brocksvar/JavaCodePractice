package org.example;

import org.example.javacollection.countofelements.CountOfElements;
import org.example.javaconcurrency.blockingqueue.BlockingQueue;
import org.example.javacore.stringbuilder.CustomStringBuilder;

public class Main {
    public static void main(String[] args) {
        blockingQueue();
    }

    private static void blockingQueue() {
        BlockingQueue<Integer> queue = new BlockingQueue<>(5);

        // Поток-производитель
        Runnable producer = () -> {
            int value = 1;
            try {
                while (true) {
                    queue.enqueue(value);
                    value++;
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        // Поток-потребитель
        Runnable consumer = () -> {
            try {
                while (true) {
                    queue.dequeue();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        // Запуск
        new Thread(producer).start();
        new Thread(consumer).start();
    }

    private static void countOfElements() {
        CountOfElements coe = new CountOfElements();
        Integer[] arr = {1, 2, 3, 4, 5, 2, 5, 2};
        System.out.println(coe.count(arr));
    }

    private static void customStringBuilder() {
        CustomStringBuilder str = new CustomStringBuilder("Hello, World!");
        System.out.println(str);
        str.append("<Append>");
        System.out.println(str);

        str.insert(5, "<Insert>");
        System.out.println(str);
        str.undo();
        System.out.println(str);
        str.undo();
        System.out.println(str);
        str.undo();
        System.out.println(str);
        str.undo();
        System.out.println(str);
    }
}