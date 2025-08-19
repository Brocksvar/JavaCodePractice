package org.example.javacore.stringbuilder;

import java.util.Arrays;

/**
 * Изучите внутреннюю реализацию класса StringBuilder и напишите свою с добавлением дополнительного метода - undo().<p>
 * Прежде чем приступать - прочитайте про паттерн snapshot и примените его в своей реализации.<p>
 * примечание: полностью переписывать все методы которые есть в StringBuilder не нужно, в задании важно именно понимание сути паттерна. В случае, если задание остаётся непонятным, задайте вопрос ментору
 */
public class CustomStringBuilder {

    private char[] value;

    private final History history;

    public CustomStringBuilder(String str) {
        value = str.toCharArray();
        history = new History();
    }

    public char[] getValue() {
        return value;
    }

    public CustomStringBuilder append(String str) {
        history.push(value);
        addStringToValue(str);
        return this;
    }

    public CustomStringBuilder insert(int offset, String str) {
        history.push(value);
        insertIntoValue(offset, str);
        return this;
    }

    public void undo() {
        value = history.pop();
    }

    private void insertIntoValue(int offset, String str) {
        int oldValueLength = value.length;
        int strLength = str.length();
        int newValueLength = oldValueLength + strLength;
        char[] leftPart = Arrays.copyOfRange(value, 0, offset);
        char[] rightPart = Arrays.copyOfRange(value, offset, oldValueLength);
        value = Arrays.copyOf(leftPart, newValueLength);
        str.getChars(0, strLength, value, offset);
        System.arraycopy(rightPart, 0, value, newValueLength - rightPart.length, rightPart.length);
    }

    private void addStringToValue(String str) {
        int oldValueLength = value.length;
        int strLength = str.length();
        int newValueLength = oldValueLength + strLength;
        value = Arrays.copyOf(value, newValueLength);
        str.getChars(0, strLength, value, oldValueLength);
    }

    @Override
    public String toString() {
        return new String(value);
    }
}
