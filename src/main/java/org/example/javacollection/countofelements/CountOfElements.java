package org.example.javacollection.countofelements;

import java.util.HashMap;
import java.util.Map;

/**
 * Напишите метод, который получает на вход массив элементов и возвращает Map ключи в котором - элементы, а значения - сколько раз встретился этот элемент
 */
public class CountOfElements {

    public <T> Map<T, Integer> count(T[] array) {
        Map<T, Integer> resultMap = new HashMap<>(array.length);
        for (T obj : array) {
            int numberOfObj = resultMap.getOrDefault(obj, 0);
            resultMap.put(obj, ++numberOfObj);
        }
        return resultMap;
    }
}
