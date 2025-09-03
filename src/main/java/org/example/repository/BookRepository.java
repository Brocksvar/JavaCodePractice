package org.example.repository;

import org.example.entity.Book;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository {
    Optional<Book> findById(UUID id);
    Book save(Book book);
    boolean existsById(UUID id);
    void deleteById(UUID id);
}
