package org.example.javaconcurrency.blockingqueue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Предположим, у вас есть пул потоков, и вы хотите реализовать блокирующую очередь для передачи задач между потоками. Создайте класс BlockingQueue, который будет обеспечивать безопасное добавление и извлечение элементов между производителями и потребителями в контексте пула потоков.<p>
 *
 * Класс BlockingQueue должен содержать методы enqueue() для добавления элемента в очередь и dequeue() для извлечения элемента. Если очередь пуста, dequeue() должен блокировать вызывающий поток до появления нового элемента.<p>
 *
 * очередь должна иметь фиксированный размер.<p>
 *
 * Используйте механизмы wait() и notify() для координации между производителями и потребителями. Реализуйте метод size(), который возвращает текущий размер очереди.
 */
public class BlockingQueue<T> {

    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void enqueue(T element) {
        try {
            if (queue.size() == capacity) {
                System.out.println("Очередь заполнена, жду");
                wait();
            }
            queue.add(element);
            System.out.println("Добавлен элемент: " + element);
            notify();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized T dequeue() {
        T element = null;
        try {
            if (queue.isEmpty()) {
                System.out.println("очередь пуста, жду");
                wait();
            }
            element = queue.poll();
            System.out.println("Получен элемент: " + element);
            notify();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        return element;
    }

    public synchronized int size() {
        return queue.size();
    }
}
