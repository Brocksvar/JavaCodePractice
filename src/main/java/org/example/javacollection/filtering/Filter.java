package org.example.javacollection.filtering;

public interface Filter {
    <T> T apply(T o);
}
