package org.example.javacore.stringbuilder;

import java.util.Stack;

class History {
    private final Stack<char[]> snapshots = new Stack<>();

    public void push(char[] snapshot) {
        snapshots.push(snapshot);
    }

    public char[] pop() {
        if (snapshots.empty()) return new char[0];
        else return snapshots.pop();
    }
}
