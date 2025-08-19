package org.example.javacollection.filtering;

import java.util.Arrays;

/**
 * Напишите метод filter, который принимает на вход массив любого типа, вторым аргументом метод должен принимать класс, реализующий интерфейс Filter, в котором один метод - T apply(T o) (параметризованный).<p>
 * Метод должен быть реализован так, чтобы возвращать новый массив, к каждому элементу которого была применена функция apply
 */
public class Filtering {

    public <T, V extends Filter> T[] filter(T[] array, V function) throws NullPointerException {
        if (array == null) throw new NullPointerException();
        int length = array.length;
        if (length == 0) return array;

        T[] resultArray = Arrays.copyOf(array, length);
        for (int i = 0; i < length; i++) {
            T resultObj = function.apply(array[i]);
            resultArray[i] = resultObj;
        }
        return resultArray;
    }
}
