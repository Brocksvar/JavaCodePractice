package org.example.repository;

import org.example.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class BookRepositoryImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BookRepositoryImpl bookRepository;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM books");
    }

    @Test
    void testSaveAndFindById() {
        Book book = new Book();
        book.setTitle("Книга");
        book.setAuthor("Автор");
        book.setPublicationYear(2008);

        Book saved = bookRepository.save(book);

        Optional<Book> found = bookRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Книга");
        assertThat(found.get().getAuthor()).isEqualTo("Автор");
        assertThat(found.get().getPublicationYear()).isEqualTo(2008);
    }

    @Test
    void testUpdateBook() {
        Book book = new Book();
        book.setTitle("Книга");
        book.setAuthor("Автор");
        book.setPublicationYear(2000);

        Book saved = bookRepository.save(book);

        saved.setTitle("Обновленное название");
        saved.setPublicationYear(2020);

        Book updated = bookRepository.save(saved);

        Optional<Book> found = bookRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Обновленное название");
        assertThat(found.get().getPublicationYear()).isEqualTo(2020);
    }

    @Test
    void testExistsById() {
        Book book = new Book();
        book.setTitle("Книга");
        book.setAuthor("Автор");
        book.setPublicationYear(2024);

        Book saved = bookRepository.save(book);

        assertThat(bookRepository.existsById(saved.getId())).isTrue();
        assertThat(bookRepository.existsById(UUID.randomUUID())).isFalse();
    }

    @Test
    void testDeleteById() {
        Book book = new Book();
        book.setTitle("Книга");
        book.setAuthor("Автор");
        book.setPublicationYear(2010);

        Book saved = bookRepository.save(book);

        assertThat(bookRepository.existsById(saved.getId())).isTrue();

        bookRepository.deleteById(saved.getId());

        assertThat(bookRepository.existsById(saved.getId())).isFalse();
    }
}
