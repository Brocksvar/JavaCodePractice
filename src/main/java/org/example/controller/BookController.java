package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.AuthorDto;
import org.example.dto.BookDetailsDto;
import org.example.dto.BookListDto;
import org.example.entity.Book;
import org.example.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public Page<BookListDto> getAllBooks(Pageable pageable) {
        return bookService.getAllBooks(pageable)
                .map(book -> new BookListDto(
                        book.getId(),
                        book.getTitle(),
                        book.getGenre(),
                        book.getYear()
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDetailsDto> getBookById(@PathVariable UUID id) {
        try {
            Book book = bookService.getBookById(id);

            BookDetailsDto dto = new BookDetailsDto(
                    book.getId(),
                    book.getTitle(),
                    book.getGenre(),
                    book.getYear(),
                    new AuthorDto(
                            book.getAuthor().getId(),
                            book.getAuthor().getName(),
                            book.getAuthor().getBiography()
                    )
            );

            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookService.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable UUID id, @RequestBody Book updatedBook) {
        try {
            return ResponseEntity.ok(bookService.updateBook(id, updatedBook));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
