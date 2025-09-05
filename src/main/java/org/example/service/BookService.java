package org.example.service;

import org.example.dto.BookDto;
import org.example.entity.Book;
import org.example.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDto getBookById(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Книга не найдена по ИДу: " + id))
                .mapToBookDto();
    }

    public BookDto createBook(Book book) {
        return bookRepository.save(book)
                .mapToBookDto();
    }

    public BookDto updateBook(UUID id, Book updatedBook) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Книга не найдена по ИДу: " + id));

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setPublicationYear(updatedBook.getPublicationYear());
        existingBook.setAuthor(updatedBook.getAuthor());

        return bookRepository.save(existingBook)
                .mapToBookDto();
    }

    public void deleteBook(UUID id) {
        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException("Книга не найдена по ИДу: " + id);
        }
        bookRepository.deleteById(id);
    }
}
