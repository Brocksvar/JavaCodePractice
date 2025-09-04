package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.dao.BookRepository;
import org.example.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Book getBookById(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Книга не найдена по ИДу: " + id));
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(UUID id, Book updatedBook) {
        Book existingBook = getBookById(id);

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setGenre(updatedBook.getGenre());
        existingBook.setYear(updatedBook.getYear());
        existingBook.setAuthor(updatedBook.getAuthor());

        return bookRepository.save(existingBook);
    }

    public void deleteBook(UUID id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Книга не найдена по ИДу: " + id);
        }
        bookRepository.deleteById(id);
    }
}
