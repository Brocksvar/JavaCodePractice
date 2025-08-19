package org.example;

import org.example.javacore.stringbuilder.CustomStringBuilder;

public class Main {
    public static void main(String[] args) {
        customStringBuilder();
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