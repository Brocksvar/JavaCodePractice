package org.example;

import org.example.javacollection.countofelements.CountOfElements;
import org.example.javacore.stringbuilder.CustomStringBuilder;

public class Main {
    public static void main(String[] args) {
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